package az.baxtiyargil.commerce.order.adapter.out.client.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Set;

@FeignClient(
        name = "product-client",
        url = "${service.product.url}",
        path = "/products"
)
public interface ProductClient {

    @GetMapping("/existing")
    ProductResponse findExistingProductIds(@RequestParam Set<Long> ids);

}
