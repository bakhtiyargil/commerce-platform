package az.baxtiyargil.commerce.product.adapter.out.persistence.mapper;

import az.baxtiyargil.commerce.product.adapter.out.persistence.ProductJpaEntity;
import az.baxtiyargil.commerce.product.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring"
)
public interface ProductPersistenceMapper {

    @Mapping(target = "details", source = "productDetails")
    Product toProduct(ProductJpaEntity entity);

}
