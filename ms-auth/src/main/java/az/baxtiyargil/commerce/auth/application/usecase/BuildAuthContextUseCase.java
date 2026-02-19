package az.baxtiyargil.commerce.auth.application.usecase;

import az.baxtiyargil.commerce.auth.application.exception.AuthErrorCodes;
import az.baxtiyargil.commerce.auth.application.exception.AuthException;
import az.baxtiyargil.commerce.auth.application.port.out.IdentityProviderPort;
import az.baxtiyargil.commerce.auth.domain.ServiceAuthContext;
import az.baxtiyargil.commerce.auth.infrastructure.AuthContextSigner;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuildAuthContextUseCase {

    private final AuthContextSigner signer;
    private final IdentityProviderPort identityProvider;

    @Value("${auth.context.ttl-seconds:30}")
    private long ttlSeconds;

    public String execute(String accessToken, String correlationId) {

        IdentityProviderPort.TokenIntrospectionResult result = identityProvider.introspect(accessToken);

        if (!result.active()) {
            throw new AuthException(AuthErrorCodes.INVALID_TOKEN,
                    "Access token is inactive or expired");
        }

        ServiceAuthContext context = ServiceAuthContext.create(
                result.userId(),
                result.email(),
                result.username(),
                result.roles(),
                result.permissions(),
                correlationId,
                ttlSeconds
        );

        return signer.sign(context);
    }
}
