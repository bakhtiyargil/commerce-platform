package az.baxtiyargil.commerce.lib.error.component;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@AutoConfiguration
public class SharedExceptionAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ErrorMessageResolver errorMessageResolver(MessageSource messageSource) {
        return new ErrorMessageResolver(messageSource);
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

}
