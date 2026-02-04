package az.baxtiyargil.commerce.order.application.usecase;

import az.baxtiyargil.commerce.order.application.port.in.PlaceOrderCommand;
import az.baxtiyargil.commerce.order.application.port.in.dto.PlaceOrderRequest;
import az.baxtiyargil.commerce.order.application.port.out.CheckCustomerPort;
import az.baxtiyargil.commerce.order.application.port.out.PersistOrderPort;
import az.baxtiyargil.commerce.order.application.usecase.mapper.OrderMapper;
import az.baxtiyargil.commerce.order.domain.error.exception.ApplicationErrorCodes;
import az.baxtiyargil.commerce.order.domain.error.exception.ApplicationException;
import az.baxtiyargil.commerce.order.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaceOrderUseCase implements PlaceOrderCommand {

    private final OrderMapper orderMapper;
    private final PersistOrderPort persistOrderPort;
    private final CheckCustomerPort checkCustomerPort;

    @Override
    public Order execute(PlaceOrderRequest request) {
//        if (!checkCustomerPort.isValid(request.getCustomerId())) {
//            throw new ApplicationException(ApplicationErrorCodes.CUSTOMER_NOT_FOUND, request.getCustomerId());
//        }

        var order = orderMapper.toOrder(request);
        return persistOrderPort.persist(order);
    }

}
