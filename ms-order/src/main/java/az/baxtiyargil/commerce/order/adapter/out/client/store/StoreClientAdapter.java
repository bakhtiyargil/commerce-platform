package az.baxtiyargil.commerce.order.adapter.out.client.store;

import az.baxtiyargil.commerce.order.application.port.out.CheckStorePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StoreClientAdapter implements CheckStorePort {

    private final StoreClient storeClient;

    @Override
    public boolean exists(Long storeId) {
        CheckStoreResponse checkStoreResponse = storeClient.existsById(storeId);
        if (checkStoreResponse == null || Objects.isNull(checkStoreResponse.exists())) {
            return false;
        }
        return checkStoreResponse.exists();
    }
}
