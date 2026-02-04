package az.baxtiyargil.commerce.order.application.port.in;

import az.baxtiyargil.commerce.order.application.port.in.dto.PlaceOrderRequest;
import az.baxtiyargil.commerce.order.domain.model.Order;

public interface PlaceOrderCommand {

    Order execute(PlaceOrderRequest placeOrderRequest);

}
