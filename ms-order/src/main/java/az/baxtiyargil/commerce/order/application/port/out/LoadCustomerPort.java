package az.baxtiyargil.commerce.order.application.port.out;

import az.baxtiyargil.commerce.order.domain.model.CustomerProfile;

public interface LoadCustomerPort {

    CustomerProfile load(Long customerId);

}
