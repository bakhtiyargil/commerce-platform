package az.baxtiyargil.commerce.auth.infrastructure;

import az.baxtiyargil.commerce.auth.adapter.out.client.KeycloakTokenClient;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({KeycloakProperties.class, AuthProperties.class})
@EnableFeignClients(clients = KeycloakTokenClient.class)
public class ApplicationConfig {

    private static final String OAUTH_GRANT_TYPE = "client_credentials";
    private final KeycloakProperties keycloakProperties;

    @Bean
    public Keycloak keycloakAdminClient() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakProperties.getServerUrl())
                .realm(keycloakProperties.getRealm())
                .clientId(keycloakProperties.getClientId())
                .clientSecret(keycloakProperties.getClientSecret())
                .grantType(OAUTH_GRANT_TYPE)
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
