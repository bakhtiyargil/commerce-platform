package az.baxtiyargil.commerce.product.adapter.out.persistence;

import az.baxtiyargil.commerce.lib.error.ApplicationException;
import az.baxtiyargil.commerce.product.adapter.out.persistence.mapper.ProductPersistenceMapper;
import az.baxtiyargil.commerce.product.application.port.out.FindExistingProductsPort;
import az.baxtiyargil.commerce.product.application.port.out.FetchProductPort;
import az.baxtiyargil.commerce.product.domain.error.EntityNotFoundErrorCodes;
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
                .orElseThrow(() -> new ApplicationException(EntityNotFoundErrorCodes.PRODUCT_NOT_FOUND, id));
        return persistenceMapper.toProduct(productEntity);
    }

    @Override
    public List<Product> fetchAll(Set<Long> ids) {
        List<ProductJpaEntity> productJpaEntities = productRepository.findAllById(ids);
        return productJpaEntities.stream()
                .map(persistenceMapper::toProduct)
                .toList();
    }

    @Override
    public Set<Long> findExisting(Set<Long> ids) {
        return productRepository.findExistingIds(ids);
    }
}
