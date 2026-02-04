package az.baxtiyargil.commerce.order;

import az.baxtiyargil.commerce.order.adapter.out.client.customer.CustomerClient;
import az.baxtiyargil.commerce.order.adapter.out.client.product.ProductClient;
import az.baxtiyargil.commerce.order.adapter.out.client.store.StoreClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(clients = {CustomerClient.class, ProductClient.class, StoreClient.class})
@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
