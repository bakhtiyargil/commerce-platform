package az.baxtiyargil.commerce.order.adapter.out.client;

import az.baxtiyargil.commerce.lib.security.component.AuthContextSigner;
import feign.Logger;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class InternalClientConfiguration {

    private final AuthContextSigner authContextSigner;

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    @Bean
    public RequestInterceptor internalAuthContextFeignInterceptor() {
        return new InternalAuthContextFeignInterceptor(authContextSigner);
    }

}
