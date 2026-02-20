package az.baxtiyargil.commerce.auth.infrastructure;

import az.baxtiyargil.commerce.auth.adapter.out.client.KeycloakTokenClient;
import az.baxtiyargil.commerce.lib.error.EnableErrorHandler;
import az.baxtiyargil.commerce.lib.error.component.ErrorMessageResolver;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableErrorHandler
@RequiredArgsConstructor
@EnableConfigurationProperties({KeycloakProperties.class, AuthProperties.class})
@EnableFeignClients(clients = KeycloakTokenClient.class)
public class ApplicationConfig {

    private static final String OAUTH_GRANT_TYPE = "client_credentials";

    private final MessageSource messageSource;
    private final KeycloakProperties keycloakProperties;

    @Bean
    public Keycloak keycloakAdminClient() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakProperties.getServerUrl())
                .realm(keycloakProperties.getRealm())
                .clientId(keycloakProperties.getClientId())
                .clientSecret(keycloakProperties.getClientSecret())
                .grantType(OAUTH_GRANT_TYPE)
                .build();
    }

    @Bean
    public ErrorMessageResolver errorMessageResolver() {
        return new ErrorMessageResolver(messageSource());
    }

    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

}
