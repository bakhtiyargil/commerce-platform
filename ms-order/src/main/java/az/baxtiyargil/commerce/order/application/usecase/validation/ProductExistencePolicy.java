package az.baxtiyargil.commerce.order.application.usecase.validation;

import az.baxtiyargil.commerce.order.application.port.in.dto.PlaceOrderRequest;
import az.baxtiyargil.commerce.order.application.port.out.CheckProductPort;
import az.baxtiyargil.commerce.order.domain.error.exception.ApplicationErrorCodes;
import az.baxtiyargil.commerce.order.domain.error.exception.ApplicationException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Order(3)
public class ProductExistencePolicy implements Policy<PlaceOrderRequest> {

    private final CheckProductPort checkProductPort;

    public ProductExistencePolicy(CheckProductPort checkProductPort) {
        this.checkProductPort = checkProductPort;
    }

    @Override
    public ValidationResult validate(PlaceOrderRequest command) {
        Set<Long> itemIds = command.getOrderItems()
                .stream()
                .map(PlaceOrderRequest.AddOrderItemRequest::getProductId)
                .collect(Collectors.toSet());
        Set<Long> existing = checkProductPort.whichExistsAmongThese(itemIds);
        itemIds.removeAll(existing);
        if (!itemIds.isEmpty()) {
            throw new ApplicationException(ApplicationErrorCodes.PRODUCT_NOT_FOUND, itemIds);
        }
        return ValidationResult.success();
    }

}
