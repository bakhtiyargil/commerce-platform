package az.baxtiyargil.commerce.product.domain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetails {

    String colour;
    String gender;
    String brand;
    String description;
    Integer[] sizes;
    String[] comments;

}
