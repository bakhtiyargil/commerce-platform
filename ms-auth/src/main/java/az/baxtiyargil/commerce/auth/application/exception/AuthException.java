package az.baxtiyargil.commerce.auth.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthException extends RuntimeException {

    private final AuthErrorCodes errorCode;
    private final HttpStatus status;

    public AuthException(AuthErrorCodes errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.status = resolveStatus(errorCode);
    }

    private static HttpStatus resolveStatus(AuthErrorCodes code) {
        return switch (code) {
            case USERNAME_TAKEN, EMAIL_TAKEN -> HttpStatus.CONFLICT;
            case INVALID_CREDENTIALS,
                 INVALID_REFRESH_TOKEN,
                 INVALID_TOKEN -> HttpStatus.UNAUTHORIZED;
            case KEYCLOAK_UNAVAILABLE -> HttpStatus.SERVICE_UNAVAILABLE;
        };
    }

    public AuthErrorCodes getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
