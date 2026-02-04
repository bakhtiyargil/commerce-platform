package az.baxtiyargil.commerce.order.adapter.mapper;

import az.baxtiyargil.commerce.order.adapter.dto.AddOrderItemRequest;
import az.baxtiyargil.commerce.order.adapter.dto.PlaceOrderRequest;
import az.baxtiyargil.commerce.order.adapter.out.persistence.OrderJpaEntity;
import az.baxtiyargil.commerce.order.domain.model.Order;
import az.baxtiyargil.commerce.order.domain.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import java.util.Collections;
import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring"
)
public interface OrderMapper {

    OrderJpaEntity toOrderJpaEntity(Order order);

    Order toOrder(OrderJpaEntity orderJpaEntity);

    Order toOrder(PlaceOrderRequest placeOrderRequest);

    OrderItem toOrderItem(AddOrderItemRequest itemRequest);

    default List<OrderItem> toOrderItems(PlaceOrderRequest request) {
        if (request == null || request.getOrderItems() == null) {
            return Collections.emptyList();
        }

        return request.getOrderItems()
                .stream()
                .map(this::toOrderItem)
                .toList();
    }
}
