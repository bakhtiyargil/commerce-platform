package az.baxtiyargil.commerce.product.application.usecase;

import az.baxtiyargil.commerce.product.application.port.in.CheckProductsExistenceQuery;
import az.baxtiyargil.commerce.product.application.port.out.FetchProductPort;
import az.baxtiyargil.commerce.product.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetProductUseCase implements CheckProductsExistenceQuery {

    private final FetchProductPort fetchProductPort;

    @Override
    public Product execute(Long id) {
        return fetchProductPort.fetch(id);
    }
}
