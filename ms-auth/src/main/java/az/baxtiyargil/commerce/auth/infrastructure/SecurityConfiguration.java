package az.baxtiyargil.commerce.auth.infrastructure;

import az.baxtiyargil.commerce.lib.error.ApplicationException;
import az.baxtiyargil.commerce.lib.error.SecurityErrorCodes;
import az.baxtiyargil.commerce.lib.error.component.RestExceptionResponseWriteHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final ApiKeyAuthFilter apiKeyAuthFilter;
    private final RestExceptionResponseWriteHandler restExceptionResponseWriteHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/api/auth/**").permitAll()
                        .requestMatchers("/v1/api/internal/**").permitAll()
                        .requestMatchers("/actuator/health").permitAll()
                        .anyRequest().denyAll()
                )
                .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(handler -> {
                            handler.accessDeniedHandler(this::onAccessDeniedErrorHandler);
                            handler.authenticationEntryPoint(this::onAccessDeniedErrorHandler);
                        }
                );

        return http.build();
    }

    private void onAccessDeniedErrorHandler(HttpServletRequest req,
                                            HttpServletResponse res,
                                            RuntimeException e) throws IOException {
        var accessDenied = new ApplicationException(SecurityErrorCodes.ACCESS_DENIED, e);
        restExceptionResponseWriteHandler.call(req, res, accessDenied);
    }

}
