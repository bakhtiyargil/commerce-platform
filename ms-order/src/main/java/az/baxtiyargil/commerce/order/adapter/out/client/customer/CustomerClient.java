package az.baxtiyargil.commerce.order.adapter.out.client.customer;

import az.baxtiyargil.commerce.order.adapter.out.client.InternalClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "customer-client",
        url = "${service.customer.url}",
        path = "/v1/customers",
        configuration = InternalClientConfiguration.class
)
public interface CustomerClient {

    @GetMapping("/{id}/existence")
    CustomerExistenceResponse checkExistenceById(@PathVariable Long id);

}
