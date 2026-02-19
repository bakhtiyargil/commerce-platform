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
@ConfigurationProperties("auth")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthProperties {

    Hmac hmac;
    Context context;
    Gateway gateway;

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Hmac {
        String secret;
    }

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Context {
        Integer ttlSeconds;
    }

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Gateway {
        String apiKey;
    }

}
