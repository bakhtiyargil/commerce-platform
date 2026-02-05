package az.baxtiyargil.commerce.product.domain.error.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private final Object[] args;
    private final ValidationErrorCodes errorCode;

    public ValidationException(ValidationErrorCodes errorCode, Object... args) {
        super(errorCode.name());
        this.errorCode = errorCode;
        this.args = args;
    }
}
