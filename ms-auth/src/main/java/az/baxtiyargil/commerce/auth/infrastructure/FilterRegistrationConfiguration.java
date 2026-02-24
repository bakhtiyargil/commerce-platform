package az.baxtiyargil.commerce.auth.infrastructure;

import az.baxtiyargil.commerce.lib.security.component.AuthContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRegistrationConfiguration {

    /**
     * Disable auto-registration of AuthContextFilter.
     * Returns a disabled FilterRegistrationBean.
     */
    @Bean
    public FilterRegistrationBean<AuthContextFilter> authContextFilterRegistration(AuthContextFilter authContextFilter) {
        FilterRegistrationBean<AuthContextFilter> registration = new FilterRegistrationBean<>(authContextFilter);
        registration.setEnabled(false);
        return registration;
    }

}
