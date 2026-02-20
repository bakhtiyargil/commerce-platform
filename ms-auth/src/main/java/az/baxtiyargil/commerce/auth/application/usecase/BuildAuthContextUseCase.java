package az.baxtiyargil.commerce.auth.application.usecase;

import az.baxtiyargil.commerce.auth.domain.error.AuthErrorCodes;
import az.baxtiyargil.commerce.auth.application.port.out.IdentityProviderPort;
import az.baxtiyargil.commerce.auth.infrastructure.AuthProperties;
import az.baxtiyargil.commerce.lib.error.AuthException;
import az.baxtiyargil.commerce.lib.security.AuthContextSigner;
import az.baxtiyargil.commerce.lib.security.ServiceAuthContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        ServiceAuthContext context = ServiceAuthContext.create(
                result.userId(),
                result.email(),
                result.username(),
                result.roles(),
                result.permissions(),
                correlationId,
                authProperties.getContext().getTtlSeconds()
        );

        return signer.sign(context);
    }
}
