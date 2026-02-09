package az.baxtiyargil.commerce.product.adapter.out.persistence;

import az.baxtiyargil.commerce.product.adapter.out.persistence.mapper.ProductPersistenceMapper;
import az.baxtiyargil.commerce.product.application.port.out.FindExistingProductsPort;
import az.baxtiyargil.commerce.product.application.port.out.FetchProductPort;
import az.baxtiyargil.commerce.product.domain.error.exception.ApplicationErrorCodes;
import az.baxtiyargil.commerce.product.domain.error.exception.ApplicationException;
import az.baxtiyargil.commerce.product.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements FetchProductPort, FindExistingProductsPort {

    private final ProductJpaRepository productRepository;
    private final ProductPersistenceMapper persistenceMapper;

    @Override
    public Product fetch(Long id) {
        ProductJpaEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCodes.PRODUCT_NOT_FOUND, id));
        return persistenceMapper.toOrder(productEntity);
    }

    @Override
    public List<Product> fetchAll(Set<Long> ids) {
        List<ProductJpaEntity> productJpaEntities = productRepository.findAllById(ids);
        return productJpaEntities.stream()
                .map(persistenceMapper::toOrder)
                .toList();
    }

    @Override
    public Set<Long> findExisting(Set<Long> ids) {
        return productRepository.findExistingIds(ids);
    }
}
