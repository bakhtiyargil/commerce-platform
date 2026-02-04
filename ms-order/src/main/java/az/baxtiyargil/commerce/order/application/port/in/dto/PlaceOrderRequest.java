package az.baxtiyargil.commerce.order.application.port.in.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PlaceOrderRequest {

    private Long storeId;
    private Long customerId;
    private List<AddOrderItemRequest> orderItems;

    @Setter
    @Getter
    public static class AddOrderItemRequest {

        private Long productId;
        private Integer quantity;
        private BigDecimal unitPrice;

    }

}
