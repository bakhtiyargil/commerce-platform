package az.baxtiyargil.commerce.order.application.usecase.validation;

import az.baxtiyargil.commerce.lib.error.ApplicationException;
import az.baxtiyargil.commerce.order.application.port.in.dto.PlaceOrderRequest;
import az.baxtiyargil.commerce.order.application.port.out.CheckStorePort;
import az.baxtiyargil.commerce.order.domain.error.ApplicationErrorCodes;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class StoreExistencePolicy implements Policy<PlaceOrderRequest> {

    private final CheckStorePort checkStorePort;

    public StoreExistencePolicy(CheckStorePort checkStorePort) {
        this.checkStorePort = checkStorePort;
    }

    @Override
    public ValidationResult validate(PlaceOrderRequest command) {
        if (!checkStorePort.exists(command.getStoreId())) {
            throw new ApplicationException(ApplicationErrorCodes.STORE_NOT_FOUND, command.getStoreId());
        }
        return ValidationResult.success();
    }

}
