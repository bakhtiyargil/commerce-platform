package az.baxtiyargil.commerce.gateway.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.ConnectException;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(-1)
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    private final ErrorResponseWriter errorResponseWriter;

    @Override
    public @NonNull Mono<Void> handle(@NonNull ServerWebExchange exchange,
                                      @NonNull Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }

        return switch (ex) {
            case AuthenticationException e -> Mono.error(e);  // let Spring Security handle
            case AccessDeniedException e -> Mono.error(e);    // let Spring Security handle
            case ConnectException e -> {
                log.error("Downstream service unavailable — path={}, reason={}",
                        exchange.getRequest().getPath().value(), e.getMessage());
                yield errorResponseWriter.write(
                        exchange,
                        HttpStatus.SERVICE_UNAVAILABLE,
                        "Service temporarily unavailable"
                );
            }
            case ResponseStatusException e -> {
                log.warn("Response status exception — status={}, path={}",
                        e.getStatusCode(), exchange.getRequest().getPath().value());
                yield errorResponseWriter.write(
                        exchange,
                        HttpStatus.valueOf(e.getStatusCode().value()),
                        e.getReason()
                );
            }
            default -> {
                log.error("Unhandled gateway error — path={}",
                        exchange.getRequest().getPath().value(), ex);
                yield errorResponseWriter.write(
                        exchange,
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "An unexpected error occurred"
                );
            }
        };
    }
}