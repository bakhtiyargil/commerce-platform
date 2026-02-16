package az.baxtiyargil.commerce.product.infrastructure.config;

import az.baxtiyargil.commerce.lib.error.EnableErrorHandler;
import az.baxtiyargil.commerce.lib.error.component.MessageResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@EnableErrorHandler
@Configuration
public class ApplicationConfiguration {

    @Bean
    public MessageResolver messageResolver() {
        return new MessageResolver(messageSource());
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

}
