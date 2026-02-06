package az.baxtiyargil.commerce.product.application.usecase;

import az.baxtiyargil.commerce.product.application.port.in.FindExistingProductsQuery;
import az.baxtiyargil.commerce.product.application.port.out.FindExistingProductsPort;
import az.baxtiyargil.commerce.product.application.usecase.dto.CheckProductsResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class FindExistingProductsUseCase implements FindExistingProductsQuery {

    private final FindExistingProductsPort findExistingProductsPort;

    @Override
    public CheckProductsResult execute(Set<Long> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            return CheckProductsResult.empty();
        }
        Set<Long> existingIds = findExistingProductsPort.findExisting(productIds);
        Set<Long> missingIds = new HashSet<>(productIds);
        missingIds.removeAll(existingIds);
        return new CheckProductsResult(existingIds, missingIds);
    }
}
