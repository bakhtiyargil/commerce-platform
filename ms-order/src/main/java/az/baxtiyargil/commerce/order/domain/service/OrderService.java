package az.baxtiyargil.commerce.order.domain.service;

import az.baxtiyargil.commerce.order.adapter.dto.PlaceOrderRequest;
import az.baxtiyargil.commerce.order.application.port.in.PlaceOrderCommand;
import az.baxtiyargil.commerce.order.application.port.out.CheckCustomerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CheckCustomerPort checkCustomerPort;
    private final PlaceOrderCommand placeOrderCommand;

    //add order command
    public void placeOrder(PlaceOrderRequest request) {
        if (!checkCustomerPort.isValid(request.getCustomerId())) {
            return;
        }
        placeOrderCommand.execute(request);
    }

}
