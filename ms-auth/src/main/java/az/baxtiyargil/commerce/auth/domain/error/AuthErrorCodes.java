package az.baxtiyargil.commerce.auth.domain.error;

import az.baxtiyargil.commerce.lib.error.ErrorCode;
import az.baxtiyargil.commerce.lib.error.RetryPolicy;
import org.springframework.http.HttpStatus;

public enum AuthErrorCodes implements ErrorCode {

    USERNAME_TAKEN("USERNAME_TAKEN", RetryPolicy.NON_RETRYABLE),
    EMAIL_TAKEN("EMAIL_TAKEN", RetryPolicy.NON_RETRYABLE),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", RetryPolicy.NON_RETRYABLE),
    INVALID_REFRESH_TOKEN("INVALID_REFRESH_TOKEN", RetryPolicy.NON_RETRYABLE),
    INVALID_TOKEN("INVALID_TOKEN", RetryPolicy.NON_RETRYABLE),
    KEYCLOAK_UNAVAILABLE("KEYCLOAK_UNAVAILABLE", RetryPolicy.NON_RETRYABLE);

    private final String message;
    private final HttpStatus status;
    private final RetryPolicy retryPolicy;

    AuthErrorCodes(String message, RetryPolicy retryPolicy) {
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
        this.retryPolicy = retryPolicy;
    }

    @Override
    public String asString() {
        return this.name();
    }

    @Override
    public HttpStatus status() {
        return this.status;
    }

    @Override
    public String message() {
        return this.message;
    }

    @Override
    public RetryPolicy retryPolicy() {
        return this.retryPolicy;
    }
}
