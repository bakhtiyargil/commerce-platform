package az.baxtiyargil.commerce.order.adapter.out.client.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "product-client",
        url = "${service.product.url}",
        path = "/v1/products"
)
public interface ProductClient {

    @PostMapping("/check-existence")
    CheckProductsResponse findExistingProductIds(@RequestBody CheckProductsRequest request);

}
