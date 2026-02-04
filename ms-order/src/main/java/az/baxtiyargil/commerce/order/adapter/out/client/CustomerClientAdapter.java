package az.baxtiyargil.commerce.order.adapter.out.client;

import az.baxtiyargil.commerce.order.adapter.out.client.mapper.CustomerMapper;
import az.baxtiyargil.commerce.order.application.port.out.CheckCustomerPort;
import az.baxtiyargil.commerce.order.application.port.out.LoadCustomerPort;
import az.baxtiyargil.commerce.order.domain.model.CustomerProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerClientAdapter implements LoadCustomerPort, CheckCustomerPort {

    private final CustomerClient customerClient;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerProfile load(Long customerId) {
        CustomerResponse customerResponse = customerClient.getCustomerById(customerId);
        return customerMapper.toCustomerProfile(customerResponse);
    }

    @Override
    public boolean isValid(Long customerId) {
        CustomerResponse customerResponse = customerClient.getCustomerById(customerId);
        return customerResponse != null;
    }
}
