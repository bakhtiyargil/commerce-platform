package az.baxtiyargil.commerce.gateway.configuration;

import az.baxtiyargil.commerce.gateway.component.AuthClientProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfiguration {

    private final AuthClientProperties authClientProperties;

    @Bean
    public WebClient authWebClient() {
        return WebClient.builder()
                .baseUrl(authClientProperties.getUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}