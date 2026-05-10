package az.baxtiyargil.commerce.gateway.configuration.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Getter
@Setter
@RefreshScope
@ConfigurationProperties("client.auth")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthClientProperties {

    String url;
    String apiKey;
    String path;

}
