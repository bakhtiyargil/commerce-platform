package az.baxtiyargil.commerce.order.adapter.out.client.product;

import az.baxtiyargil.commerce.order.adapter.out.client.InternalClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "product-client",
        url = "${service.product.url}",
        path = "/v1/products",
        configuration = InternalClientConfiguration.class
)
public interface ProductClient {

    @PostMapping("/prices")
    ProductPricesResponse fetchPrices(@RequestBody GetPricesRequest request);

}
