package az.baxtiyargil.commerce.product.adapter.in.mapper;

import az.baxtiyargil.commerce.product.adapter.in.dto.ProductWebResponse;
import az.baxtiyargil.commerce.product.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring"
)
public interface ProductWebMapper {

    ProductWebResponse toProductWebResponse(Product product);

}
