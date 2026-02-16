package az.baxtiyargil.commerce.order.domain.error;

import az.baxtiyargil.commerce.lib.error.ErrorCode;
import az.baxtiyargil.commerce.lib.error.RetryPolicy;
import org.springframework.http.HttpStatus;

public enum ValidationErrorCodes implements ErrorCode {

    ORDER_ITEMS_SIZE_EXCEEDED("ORDER_ITEMS_SIZE_EXCEEDED", RetryPolicy.NON_RETRYABLE),
    DUPLICATE_PRODUCTS("DUPLICATE_PRODUCTS", RetryPolicy.NON_RETRYABLE),
    PRODUCT_PRICE_MISMATCH("PRODUCT_PRICE_MISMATCH", RetryPolicy.NON_RETRYABLE);

    private final String message;
    private final HttpStatus status;
    private final RetryPolicy retryPolicy;

    ValidationErrorCodes(String message, RetryPolicy retryPolicy) {
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
