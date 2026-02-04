package az.baxtiyargil.commerce.order.adapter.in.dto;

import az.baxtiyargil.commerce.order.domain.model.OrderStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderWebResponse {

    Long id;
    LocalDateTime placedAt;
    Long customerId;
    OrderStatus status;
    Long storeId;
    List<OrderItemWebResponse> items;

}
