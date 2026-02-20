package az.baxtiyargil.commerce.lib.error;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private final Object[] args;
    private final ErrorCode errorCode;

    public AuthException(ErrorCode errorCode, Object... args) {
        super(errorCode.asString());
        this.errorCode = errorCode;
        this.args = args;
    }

}
