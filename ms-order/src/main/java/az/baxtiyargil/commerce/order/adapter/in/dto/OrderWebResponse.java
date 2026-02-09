package az.baxtiyargil.commerce.order.adapter.in.dto;

import az.baxtiyargil.commerce.order.domain.model.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;

public record OrderWebResponse(Long id,
                               LocalDateTime placedAt,
                               Long customerId,
                               OrderStatus status,
                               Long storeId,
                               List<OrderItemWebResponse> items) {
}
