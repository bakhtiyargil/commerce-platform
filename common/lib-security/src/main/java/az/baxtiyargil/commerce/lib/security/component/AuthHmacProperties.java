package az.baxtiyargil.commerce.lib.security.component;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Getter
@Setter
@RefreshScope
@ConfigurationProperties("auth.hmac")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthHmacProperties {

    String secret;

}
