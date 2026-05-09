package az.baxtiyargil.commerce.gateway.component;

import az.baxtiyargil.commerce.gateway.client.AuthServiceClient;
import az.baxtiyargil.commerce.gateway.jwt.JwtLocalValidator;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationGatewayFilter implements WebFilter {

    private final JwtLocalValidator jwtLocalValidator;
    private final AuthServiceClient authServiceClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var token = extractToken(exchange.getRequest());
        if (token == null) {
            return unauthorized(exchange, "Missing or invalid Authorization header");
        }

        try {
            jwtLocalValidator.validate(token);
        } catch (BadJOSEException | ParseException | JOSEException e) {
            return unauthorized(exchange, e.getMessage());
        }

        //add cache
        // phase 2 — cache check + keycloak introspection if miss
        return authServiceClient.getInternalAuthContext(token)
                .flatMap(authContextResponse -> {
                    var mutatedRequest = exchange.getRequest().mutate()
                            .headers(headers -> {
                                headers.remove(HttpHeaders.AUTHORIZATION);
                                headers.set("X-Auth-Context", authContextResponse.authContext());
                            }).build();
                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                })
                .onErrorResume(RuntimeException.class, ex -> unauthorized(exchange, ex.getMessage()));
    }

    private String extractToken(ServerHttpRequest request) {
        return Optional.ofNullable(request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(h -> h.startsWith("Bearer "))
                .map(h -> h.substring(7))
                .orElse(null);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String reason) {
        var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        var body = """
                {"code": "UNAUTHORIZED", "message": "%s"}
                """.formatted(reason);
        var buffer = response.bufferFactory().wrap(body.getBytes());
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.writeWith(Mono.just(buffer));
    }
}