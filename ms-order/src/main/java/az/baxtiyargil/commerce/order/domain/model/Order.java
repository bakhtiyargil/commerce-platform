package az.baxtiyargil.commerce.order.domain.model;

import az.baxtiyargil.commerce.order.domain.error.exception.ValidationErrorCodes;
import az.baxtiyargil.commerce.order.domain.error.exception.ValidationException;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    Long id;
    LocalDateTime placedAt;
    Long customerId;
    OrderStatus status;
    Long storeId;
    List<OrderItem> items;

    public Order(Long customerId, Long storeId, List<OrderItem> items) {
        this.customerId = customerId;
        this.storeId = storeId;
        this.status = OrderStatus.OPEN;
        this.items = validateItems(items);
    }

    private List<OrderItem> validateItems(List<OrderItem> items) {
        if (items == null || items.isEmpty() || items.size() > 100) {
            throw new ValidationException(ValidationErrorCodes.ORDER_ITEMS_SIZE_EXCEEDED, 100);
        }

        var seen = new HashSet<>();
        var exists = !items
                .stream()
                .map(OrderItem::getProductId)
                .allMatch(seen::add);
        if (exists) {
            throw new ValidationException(ValidationErrorCodes.DUPLICATE_PRODUCTS);
        }

        return items;
    }

}
