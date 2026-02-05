package az.baxtiyargil.commerce.product.adapter.out.persistence;

import az.baxtiyargil.commerce.product.adapter.out.persistence.mapper.ProductPersistenceMapper;
import az.baxtiyargil.commerce.product.application.port.out.FetchProductPort;
import az.baxtiyargil.commerce.product.domain.error.exception.ApplicationErrorCodes;
import az.baxtiyargil.commerce.product.domain.error.exception.ApplicationException;
import az.baxtiyargil.commerce.product.domain.model.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements FetchProductPort {

    private final ProductRepository productRepository;
    private final ProductPersistenceMapper persistenceMapper;

    @Transactional
    @Override
    public Product fetch(Long id) {
        ProductJpaEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCodes.CUSTOMER_NOT_FOUND, id));
        return persistenceMapper.toOrder(productEntity);
    }
}
