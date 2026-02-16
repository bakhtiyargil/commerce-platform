package az.baxtiyargil.commerce.order.domain.error;

import az.baxtiyargil.commerce.lib.error.ErrorCode;
import az.baxtiyargil.commerce.lib.error.RetryPolicy;
import org.springframework.http.HttpStatus;

public enum EntityNotFoundErrorCodes implements ErrorCode {

    PRODUCT_NOT_FOUND("PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND, RetryPolicy.NON_RETRYABLE),
    CUSTOMER_NOT_FOUND("CUSTOMER_NOT_FOUND", HttpStatus.NOT_FOUND, RetryPolicy.NON_RETRYABLE),
    STORE_NOT_FOUND("STORE_NOT_FOUND", HttpStatus.NOT_FOUND, RetryPolicy.NON_RETRYABLE);

    private final String message;
    private final HttpStatus status;
    private final RetryPolicy retryPolicy;

    EntityNotFoundErrorCodes(String message, HttpStatus httpStatus, RetryPolicy retryPolicy) {
        this.message = message;
        this.status = httpStatus;
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
