package az.baxtiyargil.commerce.product.adapter.in.mapper;

import az.baxtiyargil.commerce.product.adapter.in.dto.ProductPricesWebResponse;
import az.baxtiyargil.commerce.product.adapter.in.dto.ProductWebResponse;
import az.baxtiyargil.commerce.product.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring"
)
public interface ProductWebMapper {

    ProductWebResponse toProductWebResponse(Product product);

    ProductPricesWebResponse.ProductPrice toProductPrice(Product product);

    default ProductPricesWebResponse toProductPricesWebResponse(List<Product> products) {
        return new ProductPricesWebResponse(
                products.stream().map(this::toProductPrice).toList()
        );
    }

}
