package az.baxtiyargil.commerce.order.application.port.in.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaceOrderRequest {

    Long storeId;
    Long customerId;
    List<AddOrderItemRequest> orderItems;

    @Setter
    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class AddOrderItemRequest {

        Long productId;
        Integer quantity;
        BigDecimal unitPrice;

    }

}
