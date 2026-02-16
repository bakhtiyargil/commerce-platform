package az.baxtiyargil.commerce.lib.error.component;

import org.springframework.context.MessageSource;
import java.util.Locale;

public class ErrorMessageResolver {

    private final MessageSource messageSource;

    public ErrorMessageResolver(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(
                code,
                args,
                Locale.getDefault()
        );
    }
}
