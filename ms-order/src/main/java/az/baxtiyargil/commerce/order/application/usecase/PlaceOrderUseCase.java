package az.baxtiyargil.commerce.order.application.usecase;

import az.baxtiyargil.commerce.order.application.port.in.PlaceOrderCommand;
import az.baxtiyargil.commerce.order.application.port.in.dto.PlaceOrderRequest;
import az.baxtiyargil.commerce.order.application.port.out.PersistOrderPort;
import az.baxtiyargil.commerce.order.application.usecase.mapper.OrderMapper;
import az.baxtiyargil.commerce.order.application.usecase.validation.Policy;
import az.baxtiyargil.commerce.order.application.usecase.validation.PolicyValidator;
import az.baxtiyargil.commerce.order.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlaceOrderUseCase implements PlaceOrderCommand {

    private final OrderMapper orderMapper;
    private final PersistOrderPort persistOrderPort;
    private final List<Policy<PlaceOrderRequest>> policies;
    private final PolicyValidator<PlaceOrderRequest> policyValidator = PolicyValidator
            .withMode(PolicyValidator.Mode.FAIL_FAST_SEQUENTIAL);

    @Override
    public Order execute(PlaceOrderRequest request) {
        policyValidator.addAll(policies).executeAllFor(request);
        var order = orderMapper.toOrder(request);
        return persistOrderPort.persist(order);
    }

}
