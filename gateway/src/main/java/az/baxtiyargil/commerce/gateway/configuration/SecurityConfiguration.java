package az.baxtiyargil.commerce.gateway.configuration;

import az.baxtiyargil.commerce.gateway.client.AuthClientExceptionHandler;
import az.baxtiyargil.commerce.gateway.client.AuthServiceClient;
import az.baxtiyargil.commerce.gateway.component.AuthenticationGatewayFilter;
import az.baxtiyargil.commerce.gateway.component.ConditionalWebFilter;
import az.baxtiyargil.commerce.gateway.component.ErrorResponseWriter;
import az.baxtiyargil.commerce.gateway.configuration.properties.ApplicationProperties;
import az.baxtiyargil.commerce.gateway.jwt.JwtLocalValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtLocalValidator jwtLocalValidator;
    private final AuthServiceClient authServiceClient;
    private final ErrorResponseWriter errorResponseWriter;
    private final ApplicationProperties applicationProperties;
    private final AuthClientExceptionHandler authClientExceptionHandler;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        var publicMatcher = ServerWebExchangeMatchers.pathMatchers(applicationProperties.getIgnorePaths());
        var gatewayFilterDelegate = new AuthenticationGatewayFilter(
                jwtLocalValidator,
                authServiceClient,
                errorResponseWriter,
                authClientExceptionHandler
        );
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .matchers(publicMatcher).permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterBefore(
                        new ConditionalWebFilter(gatewayFilterDelegate, publicMatcher),
                        SecurityWebFiltersOrder.AUTHENTICATION
                )
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(this::handleAuthenticationException)
                        .accessDeniedHandler(this::handleAccessDeniedException)
                )
                .build();
    }

    private Mono<Void> handleAuthenticationException(ServerWebExchange exchange,
                                                     AuthenticationException e) {
        return errorResponseWriter.write(exchange, HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    private Mono<Void> handleAccessDeniedException(ServerWebExchange exchange,
                                                   AccessDeniedException e) {
        return errorResponseWriter.write(exchange, HttpStatus.FORBIDDEN, e.getMessage());
    }
}