package az.baxtiyargil.commerce.auth.adapter.out.client;

import az.baxtiyargil.commerce.auth.application.exception.AuthErrorCodes;
import az.baxtiyargil.commerce.auth.application.exception.AuthException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KeycloakErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        int status = response.status();

        return switch (status) {
            case 401 -> {
                log.warn("Keycloak authentication failed for method: {}", methodKey);
                yield new AuthException(AuthErrorCodes.INVALID_CREDENTIALS,
                        "Invalid username or password");
            }
            case 400 -> {
                log.warn("Keycloak bad request for method: {}", methodKey);
                yield new AuthException(AuthErrorCodes.INVALID_REFRESH_TOKEN,
                        "Invalid or expired refresh token");
            }
            case 503 -> {
                log.error("Keycloak service unavailable");
                yield new AuthException(AuthErrorCodes.KEYCLOAK_UNAVAILABLE,
                        "Identity provider temporarily unavailable");
            }
            default -> {
                log.error("Unexpected Keycloak error: status={}, method={}", status, methodKey);
                yield defaultDecoder.decode(methodKey, response);
            }
        };
    }
}
