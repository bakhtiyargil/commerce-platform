package az.baxtiyargil.commerce.auth.infrastructure;

import az.baxtiyargil.commerce.lib.security.component.BaseSecurityConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final ApiKeyAuthFilter apiKeyAuthFilter;
    private final BaseSecurityConfiguration baseSecurityConfiguration;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        baseSecurityConfiguration.applyCommonSecurity(http);
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/api/auth/**").permitAll()
                        .requestMatchers("/v1/api/internal/**").permitAll()
                        .requestMatchers("/actuator/health").permitAll()
                        .anyRequest().denyAll()
                )
                .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
