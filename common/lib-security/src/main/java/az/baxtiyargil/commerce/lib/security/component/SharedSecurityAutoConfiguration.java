package az.baxtiyargil.commerce.lib.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
@Import(BaseSecurityConfiguration.class)
@EnableConfigurationProperties(AuthHmacProperties.class)
@AutoConfiguration(before = SecurityAutoConfiguration.class)
@ComponentScan(basePackages = "az.baxtiyargil.commerce.lib.security.component")
public class SharedSecurityAutoConfiguration {

    private final AuthHmacProperties authHmacProperties;

    @Bean
    @ConditionalOnMissingBean
    public AuthContextFilter authContextFilter(AuthContextSigner authContextSigner) {
        return new AuthContextFilter(authContextSigner);
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthContextSigner authContextSigner() {
        return new AuthContextSigner(authHmacProperties.getSecret());
    }

    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService() {
        return username -> {
            throw new UsernameNotFoundException("Not supported");
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityExceptionHandler securityExceptionHandler(ObjectMapper objectMapper) {
        return new SecurityExceptionHandler(objectMapper);
    }

}
