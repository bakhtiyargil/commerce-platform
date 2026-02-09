package az.baxtiyargil.commerce.customer.infrastructure.component;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
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
