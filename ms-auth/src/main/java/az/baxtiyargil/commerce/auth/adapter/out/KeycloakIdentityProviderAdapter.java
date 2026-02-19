package az.baxtiyargil.commerce.auth.adapter.out;

import az.baxtiyargil.commerce.auth.application.port.in.RegisterCommand;
import az.baxtiyargil.commerce.auth.application.port.out.IdentityProviderPort;
import az.baxtiyargil.commerce.auth.domain.AuthToken;
import az.baxtiyargil.commerce.auth.infrastructure.KeycloakProperties;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakIdentityProviderAdapter implements IdentityProviderPort {

    private final RestTemplate restTemplate;
    private final Keycloak keycloakAdminClient;
    private final KeycloakProperties keycloakProperties;

    @Override
    public String registerUser(RegisterCommand cmd, String defaultRole) {
        RealmResource realmResource = keycloakAdminClient.realm(keycloakProperties.getRealm());

        // Build user representation
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
                throw new RuntimeException("User already exists in Keycloak");
            }
            if (response.getStatus() != 201) {
                log.error("Keycloak user creation failed — status {}", response.getStatus());
                throw new RuntimeException("Failed to create user in identity provider");
            }

            String location = response.getHeaderString("Location");
            String userId = location.substring(location.lastIndexOf('/') + 1);

            RoleRepresentation role = realmResource.roles().get(defaultRole).toRepresentation();
            realmResource.users().get(userId).roles().realmLevel().add(List.of(role));

            log.info("User registered successfully — userId={}, role={}", userId, defaultRole);
            return userId;
        }
    }

    @Override
    public AuthToken login(String username, String password) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", keycloakProperties.getClientId());
        body.add("client_secret", keycloakProperties.getClientSecret());
        body.add("username", username);
        body.add("password", password);
        body.add("scope", "openid");

        Map<?, ?> response = postToTokenEndpoint(body);
        return mapToAuthToken(response);
    }

    @Override
    public AuthToken refresh(String refreshToken) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("client_id", keycloakProperties.getClientId());
        body.add("client_secret", keycloakProperties.getClientSecret());
        body.add("refresh_token", refreshToken);

        Map<?, ?> response = postToTokenEndpoint(body);
        return mapToAuthToken(response);
    }

    @Override
    public void logout(String refreshToken) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", keycloakProperties.getClientId());
        body.add("client_secret", keycloakProperties.getClientSecret());
        body.add("refresh_token", refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        restTemplate.postForEntity(
                logoutUrl(),
                new HttpEntity<>(body, headers),
                Void.class
        );
        log.debug("Refresh token revoked successfully");
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

    @Override
    @SuppressWarnings("unchecked")
    public TokenIntrospectionResult introspect(String accessToken) {
        String introspectUrl = keycloakProperties.getServerUrl() + "/realms/" + keycloakProperties.getRealm()
                + "/protocol/openid-connect/token/introspect";

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", keycloakProperties.getClientId());
        body.add("client_secret", keycloakProperties.getClientSecret());
        body.add("token", accessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    introspectUrl,
                    new HttpEntity<>(body, headers),
                    Map.class
            );

            Map<?, ?> claims = response.getBody();
            if (claims == null || !Boolean.TRUE.equals(claims.get("active"))) {
                return TokenIntrospectionResult.inactive();
            }

            // Extract realm roles
            Set<String> roles = new HashSet<>();
            Object realmAccess = claims.get("realm_access");
            if (realmAccess instanceof Map<?, ?> ra) {
                Object roleList = ra.get("roles");
                if (roleList instanceof List<?> rl) {
                    rl.stream()
                            .filter(String.class::isInstance)
                            .map(String.class::cast)
                            // skip Keycloak internal roles
                            .filter(r -> !r.startsWith("default-roles") && !r.equals("offline_access") && !r.equals("uma_authorization"))
                            .forEach(roles::add);
                }
            }

            // Extract custom permissions claim (set via Keycloak mapper)
            Set<String> permissions = new HashSet<>();
            Object permClaim = claims.get("permissions");
            if (permClaim instanceof List<?> pl) {
                pl.stream()
                        .filter(String.class::isInstance)
                        .map(String.class::cast)
                        .forEach(permissions::add);
            }

            return new TokenIntrospectionResult(
                    true,
                    (String) claims.get("sub"),
                    (String) claims.get("email"),
                    (String) claims.get("preferred_username"),
                    Collections.unmodifiableSet(roles),
                    Collections.unmodifiableSet(permissions)
            );
        } catch (Exception e) {
            log.error("Token introspection failed", e);
            return TokenIntrospectionResult.inactive();
        }
    }

    private String tokenUrl() {
        return keycloakProperties.getServerUrl() + "/realms/"
                + keycloakProperties.getRealm() + "/protocol/openid-connect/token";
    }

    private String logoutUrl() {
        return keycloakProperties.getServerUrl() + "/realms/"
                + keycloakProperties.getRealm() + "/protocol/openid-connect/logout";
    }

    private Map<?, ?> postToTokenEndpoint(MultiValueMap<String, String> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    tokenUrl(),
                    new HttpEntity<>(body, headers),
                    Map.class
            );
            return Objects.requireNonNull(response.getBody());
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new RuntimeException("Bad credentials");
        } catch (Exception e) {
            log.error("Keycloak token endpoint call failed", e);
            throw new RuntimeException("Identity provider unavailable");
        }
    }

    private AuthToken mapToAuthToken(Map<?, ?> body) {
        return AuthToken.of(
                (String) body.get("access_token"),
                (String) body.get("refresh_token"),
                toLong(body.get("expires_in")),
                toLong(body.get("refresh_expires_in"))
        );
    }

    private long toLong(Object value) {
        return value instanceof Number n ? n.longValue() : 0L;
    }

    private CredentialRepresentation buildPasswordCredential(String password) {
        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(password);
        cred.setTemporary(false);
        return cred;
    }
}
