package az.baxtiyargil.commerce.customer.infrastructure;

import az.baxtiyargil.commerce.lib.error.ApplicationException;
import az.baxtiyargil.commerce.lib.error.SecurityErrorCodes;
import az.baxtiyargil.commerce.lib.error.component.RestExceptionResponseWriteHandler;
import az.baxtiyargil.commerce.lib.security.component.AuthContextFilter;
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

    private final AuthContextFilter authContextFilter;
    private final RestExceptionResponseWriteHandler restExceptionResponseWriteHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/api/stores").permitAll()
                        .requestMatchers("/v1/api/stores/**").permitAll()
                        .anyRequest().denyAll()
                )
                .addFilterBefore(authContextFilter, UsernamePasswordAuthenticationFilter.class)
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
