package az.baxtiyargil.commerce.order.adapter.out.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "customer-client",
        url = "${service.customer.url}",
        path = "/customers"
)
public interface CustomerClient {

    @GetMapping("/{customerId}")
    CustomerResponse getCustomerById(@PathVariable Long customerId);

}
