package az.baxtiyargil.commerce.lib.error.component;

import org.springframework.context.MessageSource;
import java.util.Locale;

public class MessageResolver {

    private final MessageSource messageSource;

    public MessageResolver(MessageSource messageSource) {
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
