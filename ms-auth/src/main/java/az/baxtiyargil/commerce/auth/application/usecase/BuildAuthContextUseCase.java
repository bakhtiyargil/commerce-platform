package az.baxtiyargil.commerce.auth.application.usecase;

import az.baxtiyargil.commerce.auth.domain.error.AuthErrorCodes;
import az.baxtiyargil.commerce.auth.application.port.out.IdentityProviderPort;
import az.baxtiyargil.commerce.auth.infrastructure.AuthProperties;
import az.baxtiyargil.commerce.lib.error.AuthException;
import az.baxtiyargil.commerce.lib.security.Permission;
import az.baxtiyargil.commerce.lib.security.Role;
import az.baxtiyargil.commerce.lib.security.component.AuthContextSigner;
import az.baxtiyargil.commerce.lib.security.ServiceAuthContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuildAuthContextUseCase {

    private final AuthContextSigner signer;
    private final AuthProperties authProperties;
    private final IdentityProviderPort identityProvider;

    public String execute(String accessToken, String correlationId) {
        IdentityProviderPort.TokenIntrospectionResult result = identityProvider.introspect(accessToken);
        if (!result.active()) {
            throw new AuthException(AuthErrorCodes.INVALID_TOKEN);
        }

        Set<Role> roles = result.roles().stream()
                .map(Role::fromName)
                .collect(Collectors.toSet());
        Set<Permission> permissions = roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .collect(Collectors.toSet());

        ServiceAuthContext context = ServiceAuthContext.create(
                result.userId(),
                result.email(),
                result.username(),
                roles,
                permissions,
                correlationId,
                authProperties.getContext().getTtlSeconds()
        );

        return signer.sign(context);
    }
}
