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
@ConfigurationProperties("security")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityProperties {

    Keycloak keycloak;

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Keycloak {
        String jwksUri;
        Long cacheTtl;
        Long cacheTimeout;
        Long rateLimitInterval;
    }

    public String getJwksUri() {
        return keycloak.getJwksUri();
    }

    public Long getCacheTtl() {
        return keycloak.getCacheTtl();
    }

    public Long getCacheTimeout() {
        return keycloak.getCacheTimeout();
    }

    public Long getRateLimitInterval() {
        return keycloak.getRateLimitInterval();
    }
}
