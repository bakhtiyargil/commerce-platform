package az.baxtiyargil.commerce.gateway.client;

import az.baxtiyargil.commerce.gateway.client.dto.AuthContextRequest;
import az.baxtiyargil.commerce.gateway.client.dto.AuthContextResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthServiceClient {

    private final WebClient authWebClient;

    public Mono<AuthContextResponse> getInternalAuthContext(String token) {
        //add a header gateway key
        var correlationId = UUID.randomUUID().toString();
        return authWebClient.post()
                .uri("/v1/auth/internal/context")
                .bodyValue(new AuthContextRequest(token, correlationId))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(new RuntimeException("Auth service returned: %s".formatted(response.statusCode())))
                )
                .bodyToMono(AuthContextResponse.class);
    }
}