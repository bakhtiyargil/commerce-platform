package az.baxtiyargil.commerce.order.application.usecase.validation;

import az.baxtiyargil.commerce.order.application.port.in.dto.PlaceOrderRequest;
import az.baxtiyargil.commerce.order.application.port.out.CheckCustomerPort;
import az.baxtiyargil.commerce.order.domain.error.exception.ApplicationErrorCodes;
import az.baxtiyargil.commerce.order.domain.error.exception.ApplicationException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class CustomerExistencePolicy implements Policy<PlaceOrderRequest> {

    private final CheckCustomerPort checkCustomerPort;

    public CustomerExistencePolicy(CheckCustomerPort checkCustomerPort) {
        this.checkCustomerPort = checkCustomerPort;
    }

    @Override
    public ValidationResult validate(PlaceOrderRequest command) {
        if (!checkCustomerPort.exists(command.getCustomerId())) {
            throw new ApplicationException(ApplicationErrorCodes.CUSTOMER_NOT_FOUND, command.getCustomerId());
        }
        return ValidationResult.success();
    }

}
