package az.baxtiyargil.commerce.gateway.component;

import az.baxtiyargil.commerce.gateway.client.AuthClientExceptionHandler;
import az.baxtiyargil.commerce.gateway.client.AuthServiceClient;
import az.baxtiyargil.commerce.gateway.jwt.JwtLocalValidator;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthenticationGatewayFilter implements WebFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTH_CONTEXT_HEADER = "X-Auth-Context";

    private final JwtLocalValidator jwtLocalValidator;
    private final AuthServiceClient authServiceClient;
    private final ErrorResponseWriter errorResponseWriter;
    private final AuthClientExceptionHandler authClientExceptionHandler;

    @Override
    public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange,
                                      @NonNull WebFilterChain chain) {
        var token = extractToken(exchange.getRequest());
        if (token == null) {
            return unauthorized(exchange, "Missing or invalid Authorization header");
        }

        try {
            jwtLocalValidator.validate(token);
        } catch (BadJOSEException | ParseException | JOSEException e) {
            return unauthorized(exchange, e.getMessage());
        }

        return authServiceClient.getInternalAuthContext(token)
                .flatMap(response -> chain
                        .filter(mutateExchange(exchange, response.authContext()))
                        .contextWrite(ReactiveSecurityContextHolder
                                .withAuthentication(buildAuthentication(response.authContext()))))
                .onErrorResume(this::isAuthServiceError,
                        ex -> authClientExceptionHandler.handle(exchange, ex));
    }

    private boolean isAuthServiceError(Throwable ex) {
        if (ex instanceof WebClientRequestException) {
            return true;
        }
        if (ex instanceof WebClientResponseException e) {
            return e.getStatusCode().is4xxClientError()
                    || e.getStatusCode().is5xxServerError();
        }
        return false;
    }

    private ServerWebExchange mutateExchange(ServerWebExchange exchange, String authContext) {
        var mutatedRequest = exchange.getRequest().mutate()
                .headers(headers -> {
                    headers.remove(HttpHeaders.AUTHORIZATION);
                    headers.set(AUTH_CONTEXT_HEADER, authContext);
                })
                .build();
        return exchange.mutate().request(mutatedRequest).build();
    }

    private PreAuthenticatedAuthenticationToken buildAuthentication(String authContext) {
        var authentication = new PreAuthenticatedAuthenticationToken(authContext, null, List.of());
        authentication.setAuthenticated(true);
        return authentication;
    }

    private String extractToken(ServerHttpRequest request) {
        return Optional.ofNullable(request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(h -> h.startsWith(BEARER_PREFIX))
                .map(h -> h.substring(BEARER_PREFIX.length()))
                .orElse(null);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String reason) {
        return errorResponseWriter.write(exchange, HttpStatus.UNAUTHORIZED, reason);
    }
}