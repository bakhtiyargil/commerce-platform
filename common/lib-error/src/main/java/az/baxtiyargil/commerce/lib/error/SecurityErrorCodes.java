package az.baxtiyargil.commerce.lib.error;

import org.springframework.http.HttpStatus;

public enum SecurityErrorCodes implements ErrorCode {

    ACCESS_DENIED("ACCESS_DENIED", RetryPolicy.NON_RETRYABLE);

    private final String message;
    private final HttpStatus status;
    private final RetryPolicy retryPolicy;

    SecurityErrorCodes(String message, RetryPolicy retryPolicy) {
        this.message = message;
        this.status = HttpStatus.FORBIDDEN;
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
