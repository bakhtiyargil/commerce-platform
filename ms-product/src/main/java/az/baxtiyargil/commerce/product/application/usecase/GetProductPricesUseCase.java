package az.baxtiyargil.commerce.product.application.usecase;

import az.baxtiyargil.commerce.product.application.port.in.GetProductPricesQuery;
import az.baxtiyargil.commerce.product.application.port.out.FetchProductPort;
import az.baxtiyargil.commerce.product.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class GetProductPricesUseCase implements GetProductPricesQuery {

    private final FetchProductPort fetchProductPort;

    @Override
    public List<Product> execute(Set<Long> ids) {
        return fetchProductPort.fetchAll(ids);
    }
}
