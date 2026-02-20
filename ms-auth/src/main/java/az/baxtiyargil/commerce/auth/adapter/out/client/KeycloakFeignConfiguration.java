package az.baxtiyargil.commerce.auth.adapter.out.client;

import feign.Logger;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;

public class KeycloakFeignConfiguration {

    /**
     * Form encoder — converts @RequestBody POJOs to application/x-www-form-urlencoded
     */
    @Bean
    public Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> converters) {
        return new SpringFormEncoder(new SpringEncoder(converters));
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new KeycloakErrorDecoder();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
