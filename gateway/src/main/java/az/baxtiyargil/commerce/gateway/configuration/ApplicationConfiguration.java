package az.baxtiyargil.commerce.gateway.configuration;

import az.baxtiyargil.commerce.gateway.configuration.properties.ApplicationProperties;
import az.baxtiyargil.commerce.gateway.configuration.properties.AuthClientProperties;
import az.baxtiyargil.commerce.gateway.configuration.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        AuthClientProperties.class, SecurityProperties.class, ApplicationProperties.class
})
public class ApplicationConfiguration {
}
