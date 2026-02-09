package az.baxtiyargil.commerce.product.application.usecase;

import az.baxtiyargil.commerce.product.application.port.in.FindExistingProductsQuery;
import az.baxtiyargil.commerce.product.application.port.out.FindExistingProductsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class FindExistingProductsUseCase implements FindExistingProductsQuery {

    private final FindExistingProductsPort findExistingProductsPort;

    @Override
    public Set<Long> execute(Set<Long> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            return Collections.emptySet();
        }
        return findExistingProductsPort.findExisting(productIds);
    }
}
