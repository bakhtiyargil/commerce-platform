package az.baxtiyargil.commerce.auth.adapter.out;

import az.baxtiyargil.commerce.auth.adapter.out.client.IntrospectionRequest;
import az.baxtiyargil.commerce.auth.adapter.out.client.IntrospectionResponse;
import az.baxtiyargil.commerce.auth.adapter.out.client.KeycloakTokenClient;
import az.baxtiyargil.commerce.auth.adapter.out.client.LogoutRequest;
import az.baxtiyargil.commerce.auth.adapter.out.client.PasswordGrantRequest;
import az.baxtiyargil.commerce.auth.adapter.out.client.RefreshTokenRequest;
import az.baxtiyargil.commerce.auth.adapter.out.client.TokenResponse;
import az.baxtiyargil.commerce.auth.domain.error.AuthErrorCodes;
import az.baxtiyargil.commerce.auth.application.port.in.RegisterCommand;
import az.baxtiyargil.commerce.auth.application.port.out.IdentityProviderPort;
import az.baxtiyargil.commerce.auth.domain.AuthToken;
import az.baxtiyargil.commerce.auth.infrastructure.KeycloakProperties;
import az.baxtiyargil.commerce.lib.error.AuthException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakIdentityProviderAdapter implements IdentityProviderPort {

    private final Keycloak keycloakAdminClient;
    private final KeycloakProperties keycloakProperties;
    private final KeycloakTokenClient keycloakTokenClient;

    @Override
    public String registerUser(RegisterCommand cmd, String defaultRole) {
        RealmResource realmResource = keycloakAdminClient.realm(keycloakProperties.getRealm());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(cmd.username());
        user.setEmail(cmd.email());
        user.setFirstName(cmd.firstName());
        user.setLastName(cmd.lastName());
        user.setEnabled(true);
        user.setEmailVerified(false);
        user.setCredentials(List.of(buildPasswordCredential(cmd.password())));

        try (Response response = realmResource.users().create(user)) {
            if (response.getStatus() == 409) {
                throw new AuthException(AuthErrorCodes.USERNAME_TAKEN);
            }
            if (response.getStatus() != 201) {
                log.error("Keycloak user creation failed — status {}", response.getStatus());
                throw new AuthException(AuthErrorCodes.KEYCLOAK_UNAVAILABLE);
            }

            String location = response.getHeaderString("Location");
            String userId = location.substring(location.lastIndexOf('/') + 1);
            RoleRepresentation role = realmResource.roles().get(defaultRole).toRepresentation();
            realmResource.users().get(userId).roles().realmLevel().add(List.of(role));
            log.info("User registered — userId={}, role={}", userId, defaultRole);
            return userId;
        }
    }

    @Override
    public AuthToken login(String username, String password) {
        PasswordGrantRequest request = PasswordGrantRequest.builder()
                .grantType("password")
                .clientId(keycloakProperties.getClientId())
                .clientSecret(keycloakProperties.getClientSecret())
                .username(username)
                .password(password)
                .scope("openid")
                .build();

        TokenResponse response = keycloakTokenClient.login(request);
        return mapToAuthToken(response);
    }

    @Override
    public AuthToken refresh(String refreshToken) {
        RefreshTokenRequest request = RefreshTokenRequest.builder()
                .grantType("refresh_token")
                .clientId(keycloakProperties.getClientId())
                .clientSecret(keycloakProperties.getClientSecret())
                .refreshToken(refreshToken)
                .build();

        TokenResponse response = keycloakTokenClient.refresh(request);
        return mapToAuthToken(response);
    }

    @Override
    public void logout(String refreshToken) {
        LogoutRequest request = LogoutRequest.builder()
                .clientId(keycloakProperties.getClientId())
                .clientSecret(keycloakProperties.getClientSecret())
                .refreshToken(refreshToken)
                .build();

        keycloakTokenClient.logout(request);
        log.debug("Refresh token revoked successfully");
    }

    @Override
    @SuppressWarnings("unchecked")
    public TokenIntrospectionResult introspect(String accessToken) {
        IntrospectionRequest request = IntrospectionRequest.builder()
                .clientId(keycloakProperties.getClientId())
                .clientSecret(keycloakProperties.getClientSecret())
                .token(accessToken)
                .build();

        IntrospectionResponse response = keycloakTokenClient.introspect(request);
        if (!Boolean.TRUE.equals(response.getActive())) {
            return TokenIntrospectionResult.inactive();
        }

        Set<String> roles = new HashSet<>();
        if (response.getRealmAccess() != null) {
            Object roleList = response.getRealmAccess().get(KeycloakConstant.ROLES);
            if (roleList instanceof List<?> rl) {
                rl.stream()
                        .filter(String.class::isInstance)
                        .map(String.class::cast)
                        .filter(r -> !r.startsWith("default-roles") &&
                                !r.equals("offline_access") &&
                                !r.equals("uma_authorization"))
                        .forEach(roles::add);
            }
        }

        Set<String> permissions = response.getPermissions() != null
                ? new HashSet<>(response.getPermissions())
                : new HashSet<>();

        return new TokenIntrospectionResult(true,
                response.getSub(),
                response.getEmail(),
                response.getPreferredUsername(),
                Collections.unmodifiableSet(roles),
                Collections.unmodifiableSet(permissions)
        );
    }

    @Override
    public boolean usernameExists(String username) {
        return !keycloakAdminClient.realm(keycloakProperties.getRealm())
                .users()
                .searchByUsername(username, true)
                .isEmpty();
    }

    @Override
    public boolean emailExists(String email) {
        return !keycloakAdminClient.realm(keycloakProperties.getRealm())
                .users()
                .searchByEmail(email, true)
                .isEmpty();
    }

    private AuthToken mapToAuthToken(TokenResponse response) {
        return AuthToken.of(
                response.getAccessToken(),
                response.getRefreshToken(),
                response.getExpiresIn(),
                response.getRefreshExpiresIn()
        );
    }

    private CredentialRepresentation buildPasswordCredential(String password) {
        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(password);
        cred.setTemporary(false);
        return cred;
    }
}
