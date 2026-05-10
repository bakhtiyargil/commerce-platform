package az.baxtiyargil.commerce.gateway.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ErrorResponseWriter {

    private final ObjectMapper objectMapper;

    public Mono<Void> write(ServerWebExchange exchange, HttpStatus status, String message) {
        var request = exchange.getRequest();
        var response = exchange.getResponse();
        var correlationId = Optional.ofNullable(request.getHeaders().getFirst("X-Correlation-Id"))
                .orElse(UUID.randomUUID().toString());
        var path = request.getPath().value();

        log.warn("Security error — correlationId={}, status={}, code={}, path={}, reason={}",
                correlationId, status.value(), status.name(), path, message);

        var errorBody = Map.of(
                "id", correlationId,
                "code", status.name(),
                "message", message != null ? message : status.getReasonPhrase(),
                "status", status.value(),
                "timestamp", Instant.now().toString(),
                "path", path,
                "properties", Collections.emptyList()
        );

        try {
            var bytes = objectMapper.writeValueAsBytes(errorBody);
            var buffer = response.bufferFactory().wrap(bytes);
            response.setStatusCode(status);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize error response — correlationId={}", correlationId, e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return response.setComplete();
        }
    }
}