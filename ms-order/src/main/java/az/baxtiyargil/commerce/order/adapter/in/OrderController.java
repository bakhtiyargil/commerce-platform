package az.baxtiyargil.commerce.order.adapter.in;

import az.baxtiyargil.commerce.order.adapter.dto.PlaceOrderRequest;
import az.baxtiyargil.commerce.order.domain.service.OrderService;
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

    private final OrderService orderService;

    @PostMapping
    public void placeOrder(@Valid @RequestBody PlaceOrderRequest placeOrderRequest) {
        orderService.placeOrder(placeOrderRequest);
    }

}
