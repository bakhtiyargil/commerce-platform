package az.baxtiyargil.commerce.order.adapter.in;

import az.baxtiyargil.commerce.order.adapter.in.dto.OrderWebResponse;
import az.baxtiyargil.commerce.order.adapter.in.dto.PlaceOrderWebRequest;
import az.baxtiyargil.commerce.order.adapter.in.mapper.OrderWebMapper;
import az.baxtiyargil.commerce.order.application.port.in.dto.PlaceOrderRequest;
import az.baxtiyargil.commerce.order.application.usecase.PlaceOrderUseCase;
import az.baxtiyargil.commerce.order.domain.model.Order;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final PlaceOrderUseCase placeOrder;
    private final OrderWebMapper orderWebMapper;

    @PostMapping
    public OrderWebResponse placeOrder(@Valid @RequestBody PlaceOrderWebRequest placeOrderWebRequest) {
        PlaceOrderRequest placeOrderRequest = orderWebMapper.toOrder(placeOrderWebRequest);
        Order order = placeOrder.execute(placeOrderRequest);
        return orderWebMapper.toOrderWebResponse(order);
    }

}
