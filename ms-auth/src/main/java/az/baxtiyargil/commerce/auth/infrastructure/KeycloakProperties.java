package az.baxtiyargil.commerce.auth.infrastructure;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Getter
@Setter
@RefreshScope
@ConfigurationProperties("keycloak")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KeycloakProperties {

    String serverUrl;
    String realm;
    String clientId;
    String clientSecret;

}
