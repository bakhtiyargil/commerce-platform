package az.baxtiyargil.commerce.order.application.usecase.mapper;

import az.baxtiyargil.commerce.order.application.port.in.dto.PlaceOrderRequest;
import az.baxtiyargil.commerce.order.domain.model.Order;
import az.baxtiyargil.commerce.order.domain.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring"
)
public interface OrderMapper {

    @Mapping(target = "items", source = "orderItems")
    Order toOrder(PlaceOrderRequest placeOrderRequest);

    OrderItem toOrderItem(PlaceOrderRequest.AddOrderItemRequest addOrderItemRequest);

}
