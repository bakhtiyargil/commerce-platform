package az.baxtiyargil.commerce.order.infrastructure;

import az.baxtiyargil.commerce.order.adapter.out.client.customer.CustomerClient;
import az.baxtiyargil.commerce.order.adapter.out.client.product.ProductClient;
import az.baxtiyargil.commerce.order.adapter.out.client.store.StoreClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(clients = {
        CustomerClient.class,
        ProductClient.class,
        StoreClient.class
})
@Configuration
public class FeignConfiguration {
}
