package az.baxtiyargil.commerce.order.domain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {

    Integer id;
    Long orderId;
    Long productId;
    BigDecimal unitPrice;
    Integer quantity;
    Long shipmentId;

}
