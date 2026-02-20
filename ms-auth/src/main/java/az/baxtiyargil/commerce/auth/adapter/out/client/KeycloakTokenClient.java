package az.baxtiyargil.commerce.auth.adapter.out.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "keycloak-token-client",
        url = "${keycloak.server-url}/realms/${keycloak.realm}/protocol/openid-connect",
        configuration = KeycloakFeignConfiguration.class
)
public interface KeycloakTokenClient {

    @PostMapping(value = "/token", consumes = "application/x-www-form-urlencoded")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    TokenResponse login(@RequestBody PasswordGrantRequest request);

    @PostMapping(value = "/token", consumes = "application/x-www-form-urlencoded")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    TokenResponse refresh(@RequestBody RefreshTokenRequest request);

    @PostMapping(value = "/token", consumes = "application/x-www-form-urlencoded")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    TokenResponse clientCredentials(@RequestBody ClientCredentialsRequest request);

    @PostMapping(value = "/token/introspect", consumes = "application/x-www-form-urlencoded")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    IntrospectionResponse introspect(@RequestBody IntrospectionRequest request);

    @PostMapping(value = "/logout", consumes = "application/x-www-form-urlencoded")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    void logout(@RequestBody LogoutRequest request);

}
