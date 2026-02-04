package az.baxtiyargil.commerce.order.adapter.in.mapper;

import az.baxtiyargil.commerce.order.adapter.in.dto.AddOrderItemWebRequest;
import az.baxtiyargil.commerce.order.adapter.in.dto.OrderItemWebResponse;
import az.baxtiyargil.commerce.order.adapter.in.dto.OrderWebResponse;
import az.baxtiyargil.commerce.order.adapter.in.dto.PlaceOrderWebRequest;
import az.baxtiyargil.commerce.order.application.port.in.dto.PlaceOrderRequest;
import az.baxtiyargil.commerce.order.domain.model.Order;
import az.baxtiyargil.commerce.order.domain.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring"
)
public interface OrderWebMapper {

    PlaceOrderRequest toOrder(PlaceOrderWebRequest placeOrderWebRequest);

    PlaceOrderRequest.AddOrderItemRequest toOrderItem(AddOrderItemWebRequest itemWebRequest);

    OrderWebResponse toOrderWebResponse(Order order);

    OrderItemWebResponse toOrderItemWebResponse(OrderItem orderItem);

}
