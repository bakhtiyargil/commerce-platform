package az.baxtiyargil.commerce.gateway.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Getter
@Setter
@RefreshScope
@ConfigurationProperties(value = "application")
public class ApplicationProperties {

    private String[] ignorePaths;

}
