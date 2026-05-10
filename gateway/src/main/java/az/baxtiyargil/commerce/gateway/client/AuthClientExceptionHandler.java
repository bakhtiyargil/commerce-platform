package az.baxtiyargil.commerce.gateway.client;

import az.baxtiyargil.commerce.gateway.component.ErrorResponseWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthClientExceptionHandler {

    private final ErrorResponseWriter errorResponseWriter;

    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        return switch (ex) {
            case WebClientResponseException e when e.getStatusCode().is4xxClientError() ->
                    forwardAuthServiceError(exchange, e);
            case WebClientResponseException e when e.getStatusCode().is5xxServerError() -> {
                log.error("Auth service internal error — status={}, path={}", e.getStatusCode(), exchange.getRequest().getPath().value(), e);
                yield serviceUnavailable(exchange);
            }
            default -> {
                log.error("Auth service unreachable — path={}", exchange.getRequest().getPath().value(), ex);
                yield serviceUnavailable(exchange);
            }
        };
    }

    private Mono<Void> forwardAuthServiceError(ServerWebExchange exchange,
                                               WebClientResponseException ex) {
        log.warn("Auth service returned 4xx — status={}, path={}",
                ex.getStatusCode(), exchange.getRequest().getPath().value());

        var response = exchange.getResponse();
        response.setStatusCode(ex.getStatusCode());
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        var buffer = response.bufferFactory().wrap(ex.getResponseBodyAsByteArray());
        return response.writeWith(Mono.just(buffer));
    }

    private Mono<Void> serviceUnavailable(ServerWebExchange exchange) {
        return errorResponseWriter.write(
                exchange,
                HttpStatus.SERVICE_UNAVAILABLE,
                "Auth service unavailable"
        );
    }
}