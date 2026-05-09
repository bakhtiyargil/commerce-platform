package az.baxtiyargil.commerce.gateway.jwt;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.JWKSourceBuilder;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.MalformedURLException;
import java.net.URI;

@Configuration
public class JwksConfiguration {

    @Bean
    public JWKSource<SecurityContext> jwkSource(@Value("${keycloak.jwks-uri}") String jwksUri)
            throws MalformedURLException {
        return JWKSourceBuilder
                .create(URI.create(jwksUri).toURL())
                .retrying(true)
                .cache(15 * 60 * 1000L, 5 * 60 * 1000L)
                .rateLimited(30 * 60 * 1000L)
                .build();
    }
}