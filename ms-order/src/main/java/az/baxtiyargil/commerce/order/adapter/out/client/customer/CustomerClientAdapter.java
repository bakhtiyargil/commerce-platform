package az.baxtiyargil.commerce.order.adapter.out.client.customer;

import az.baxtiyargil.commerce.order.application.port.out.CheckCustomerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerClientAdapter implements CheckCustomerPort {

    private final CustomerClient customerClient;

    @Override
    public boolean exists(Long customerId) {
        CustomerExistenceResponse customerExistenceResponse = customerClient.checkExistenceById(customerId);
        if (customerExistenceResponse == null || Objects.isNull(customerExistenceResponse.exists())) {
            return false;
        }
        return customerExistenceResponse.exists();
    }
}
