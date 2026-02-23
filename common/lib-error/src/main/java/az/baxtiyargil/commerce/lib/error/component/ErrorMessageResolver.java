package az.baxtiyargil.commerce.lib.error.component;

import az.baxtiyargil.commerce.lib.error.ErrorCode;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import java.util.Locale;

public class ErrorMessageResolver {

    private final MessageSource messageSource;

    public ErrorMessageResolver(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(ErrorCode code, Object[] args) {
        try {
            return messageSource.getMessage(code.message(), args, Locale.getDefault());
        } catch (NoSuchMessageException exception) {
            return code.message();
        }
    }
}
