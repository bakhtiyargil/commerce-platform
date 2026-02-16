package az.baxtiyargil.commerce.lib.error;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final Object[] args;
    private final ErrorCode errorCode;

    public ApplicationException(ErrorCode errorCode, Object... args) {
        super(errorCode.asString());
        this.errorCode = errorCode;
        this.args = args;
    }
}
