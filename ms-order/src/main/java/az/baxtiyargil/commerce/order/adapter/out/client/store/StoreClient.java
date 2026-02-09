package az.baxtiyargil.commerce.order.adapter.out.client.store;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "store-client",
        url = "${service.store.url}",
        path = "/v1/stores"
)
public interface StoreClient {

    @GetMapping("/{storeId}/exists")
    CheckStoreResponse existsById(@PathVariable Long storeId);

}
