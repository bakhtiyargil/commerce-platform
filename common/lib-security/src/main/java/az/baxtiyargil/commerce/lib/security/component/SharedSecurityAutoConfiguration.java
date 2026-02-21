package az.baxtiyargil.commerce.lib.security.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;

@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthHmacProperties.class)
@ComponentScan(basePackages = "az.baxtiyargil.commerce.lib.security.component")
public class SharedSecurityAutoConfiguration {

    private final AuthHmacProperties authHmacProperties;

    @Bean
    @Order(100)
    @ConditionalOnMissingBean
    public AuthContextFilter authContextFilter(AuthContextSigner authContextSigner) {
        return new AuthContextFilter(authContextSigner);
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthContextSigner authContextSigner() {
        return new AuthContextSigner(authHmacProperties.getSecret());
    }

}
