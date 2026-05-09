package az.baxtiyargil.commerce.gateway.configuration;

import az.baxtiyargil.commerce.gateway.component.SecurityProperties;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.JWKSourceBuilder;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.MalformedURLException;
import java.net.URI;

@Configuration
@RequiredArgsConstructor
public class JwksConfiguration {

    private final SecurityProperties security;

    @Bean
    public JWKSource<SecurityContext> jwkSource()
            throws MalformedURLException {
        return JWKSourceBuilder
                .create(URI.create(security.getJwksUri()).toURL())
                .retrying(true)
                .cache(security.getCacheTtl(), security.getCacheTimeout())
                .rateLimited(security.getRateLimitInterval())
                .build();
    }
}