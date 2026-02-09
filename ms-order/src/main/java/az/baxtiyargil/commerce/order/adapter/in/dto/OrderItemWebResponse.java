package az.baxtiyargil.commerce.order.adapter.in.dto;

import java.math.BigDecimal;

public record OrderItemWebResponse(Integer id,
                                   Long orderId,
                                   Long productId,
                                   BigDecimal unitPrice,
                                   Integer quantity,
                                   Long shipmentId) {
}
