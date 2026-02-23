package az.baxtiyargil.commerce.order.adapter.out.client.store;

import az.baxtiyargil.commerce.order.adapter.out.client.InternalClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "store-client",
        url = "${service.store.url}",
        path = "/v1/api/stores",
        configuration = InternalClientConfiguration.class
)
public interface StoreClient {

    @GetMapping("/{storeId}/exists")
    CheckStoreResponse existsById(@PathVariable Long storeId);

}
