package az.baxtiyargil.commerce.order.adapter.in.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemWebResponse {

    Integer id;
    Long orderId;
    Long productId;
    BigDecimal unitPrice;
    Integer quantity;
    Long shipmentId;

}
