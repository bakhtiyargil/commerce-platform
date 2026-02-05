package az.baxtiyargil.commerce.product.domain.error.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final Object[] args;
    private final ApplicationErrorCodes errorCode;

    public ApplicationException(ApplicationErrorCodes errorCode, Object... args) {
        super(errorCode.name());
        this.errorCode = errorCode;
        this.args = args;
    }
}
