package az.baxtiyargil.commerce.customer.application.usecase;

import az.baxtiyargil.commerce.customer.application.port.in.CheckCustomerExistenceQuery;
import az.baxtiyargil.commerce.customer.application.port.out.CheckCustomerExistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckCustomerUseCase implements CheckCustomerExistenceQuery {

    private final CheckCustomerExistencePort customerExistencePort;

    @Override
    public Boolean exists(Long id) {
        return customerExistencePort.exists(id);
    }
}
