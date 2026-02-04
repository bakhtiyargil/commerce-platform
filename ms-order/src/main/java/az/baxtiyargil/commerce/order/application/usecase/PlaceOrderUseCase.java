package az.baxtiyargil.commerce.order.application.usecase;

import az.baxtiyargil.commerce.order.adapter.dto.PlaceOrderRequest;
import az.baxtiyargil.commerce.order.adapter.mapper.OrderMapper;
import az.baxtiyargil.commerce.order.application.port.in.PlaceOrderCommand;
import az.baxtiyargil.commerce.order.application.port.out.PersistOrderPort;
import az.baxtiyargil.commerce.order.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaceOrderUseCase implements PlaceOrderCommand {

    private final OrderMapper orderMapper;
    private final PersistOrderPort persistOrderPort;

    @Override
    public Order execute(PlaceOrderRequest request) {
        var orderItems = orderMapper.toOrderItems(request);
        var order = orderMapper.toOrder(request);
        order.setItems(orderItems);
        return persistOrderPort.persist(order);
    }

}
