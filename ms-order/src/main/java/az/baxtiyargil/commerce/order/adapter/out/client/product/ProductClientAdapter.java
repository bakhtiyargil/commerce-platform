package az.baxtiyargil.commerce.order.adapter.out.client.product;

import az.baxtiyargil.commerce.order.application.port.out.CheckProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductClientAdapter implements CheckProductPort {

    private final ProductClient productClient;

    @Override
    public Map<Long, BigDecimal> findProductsWithPrices(Set<Long> productIds) {
        ProductPricesResponse productResponse = productClient.getProductPrices(
                new CheckProductsRequest(productIds)
        );
        return productResponse.getProductPrices()
                .stream()
                .collect(Collectors.toMap(
                        ProductPricesResponse.ProductPrice::getId,
                        ProductPricesResponse.ProductPrice::getUnitPrice)
                );
    }

}
