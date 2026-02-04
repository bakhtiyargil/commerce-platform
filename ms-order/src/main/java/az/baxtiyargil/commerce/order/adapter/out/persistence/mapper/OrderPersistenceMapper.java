package az.baxtiyargil.commerce.order.adapter.out.persistence.mapper;

import az.baxtiyargil.commerce.order.adapter.out.persistence.OrderItemId;
import az.baxtiyargil.commerce.order.adapter.out.persistence.OrderItemJpaEntity;
import az.baxtiyargil.commerce.order.adapter.out.persistence.OrderJpaEntity;
import az.baxtiyargil.commerce.order.domain.model.Order;
import az.baxtiyargil.commerce.order.domain.model.OrderItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import java.util.stream.IntStream;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring"
)
public interface OrderPersistenceMapper {

    Order toOrder(OrderJpaEntity orderJpaEntity);

    @Mapping(target = "orderItems", ignore = true)
    OrderJpaEntity toOrderJpaEntity(Order order);

    @Mapping(target = "id", ignore = true)
    OrderItemJpaEntity toOrderItemJpaEntity(OrderItem item);

    @AfterMapping
    default void linkItems(Order order, @MappingTarget OrderJpaEntity orderJpaEntity) {
        IntStream.range(0, order.getItems().size())
                .forEach(i -> {
                    OrderItem orderItem = order.getItems().get(i);
                    OrderItemId id = new OrderItemId();
                    id.setOrderId(orderJpaEntity.getId());
                    id.setLineItemId(i + 1);

                    OrderItemJpaEntity orderItemJpaEntity = toOrderItemJpaEntity(orderItem);
                    orderItemJpaEntity.setId(id);
                    orderJpaEntity.addItem(orderItemJpaEntity);
                });
    }

}
