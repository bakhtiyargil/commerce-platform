package az.baxtiyargil.commerce.product.adapter.in.dto;

import az.baxtiyargil.commerce.product.domain.model.ProductDetails;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductWebResponse {

    Long id;
    String name;
    BigDecimal unitPrice;
    ProductDetails details;

}
