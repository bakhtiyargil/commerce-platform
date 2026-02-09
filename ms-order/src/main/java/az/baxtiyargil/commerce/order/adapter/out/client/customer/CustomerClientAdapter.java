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
        CheckCustomerResponse checkCustomerResponse = customerClient.existsById(customerId);
        if (checkCustomerResponse == null || Objects.isNull(checkCustomerResponse.exists())) {
            return false;
        }
        return checkCustomerResponse.exists();
    }
}
