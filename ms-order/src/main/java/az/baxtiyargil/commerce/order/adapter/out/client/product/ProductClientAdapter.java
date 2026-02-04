package az.baxtiyargil.commerce.order.adapter.out.client.product;

import az.baxtiyargil.commerce.order.application.port.out.CheckProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductClientAdapter implements CheckProductPort {

    private final ProductClient productClient;

    @Override
    public Set<Long> whichExistsAmongThese(Set<Long> productIds) {
        ProductResponse productResponse = productClient.findExistingProductIds(GetProductRequest.withIds(productIds));
        productIds.removeAll(productResponse.getProductIds());
        return productIds;
    }

}
