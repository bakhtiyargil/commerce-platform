package az.baxtiyargil.commerce.gateway.client;

import az.baxtiyargil.commerce.gateway.client.dto.AuthContextRequest;
import az.baxtiyargil.commerce.gateway.client.dto.AuthContextResponse;
import az.baxtiyargil.commerce.gateway.configuration.properties.AuthClientProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthServiceClient {

    private static final String apiKeyHeader = "X-Gateway-Api-Key";

    private final WebClient authWebClient;
    private final AuthClientProperties authClientProperties;

    public Mono<AuthContextResponse> getInternalAuthContext(String token) {
        var correlationId = UUID.randomUUID().toString();
        return authWebClient.post()
                .uri(authClientProperties.getPath())
                .bodyValue(new AuthContextRequest(token, correlationId))
                .header(apiKeyHeader, authClientProperties.getApiKey())
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(new RuntimeException("Auth service returned: %s".formatted(response.statusCode())))
                )
                .bodyToMono(AuthContextResponse.class);
    }
}