package az.baxtiyargil.commerce.product.domain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    Long id;
    String name;
    BigDecimal unitPrice;
    ProductDetails details;
    byte[] image;
    String imageMimeType;
    String imageFileName;
    String imageCharset;
    LocalDateTime imageLastUpdated;

}
