package az.baxtiyargil.commerce.product.adapter.out.persistence.mapper;

import az.baxtiyargil.commerce.product.adapter.out.persistence.ProductJpaEntity;
import az.baxtiyargil.commerce.product.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring"
)
public interface ProductPersistenceMapper {

    Product toOrder(ProductJpaEntity entity);

}
