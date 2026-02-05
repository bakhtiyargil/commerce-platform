package az.baxtiyargil.commerce.product.domain.error.exception;

import org.springframework.http.HttpStatus;

public enum ApplicationErrorCodes implements ErrorCode {

    ORDER_NOT_FOUND("ORDER_NOT_FOUND", RetryPolicy.NON_RETRYABLE),
    INVENTORY_NOT_FOUND("INVENTORY_NOT_FOUND", RetryPolicy.NON_RETRYABLE),
    PRODUCT_NOT_FOUND("PRODUCT_NOT_FOUND", RetryPolicy.NON_RETRYABLE),
    CUSTOMER_NOT_FOUND("CUSTOMER_NOT_FOUND", RetryPolicy.NON_RETRYABLE),
    STORE_NOT_FOUND("STORE_NOT_FOUND", RetryPolicy.NON_RETRYABLE),
    INSUFFICIENT_INVENTORY("INSUFFICIENT_INVENTORY", RetryPolicy.RETRYABLE);

    private final String message;
    private final HttpStatus status;
    private final RetryPolicy retryPolicy;

    ApplicationErrorCodes(String message, RetryPolicy retryPolicy) {
        this.message = message;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
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
