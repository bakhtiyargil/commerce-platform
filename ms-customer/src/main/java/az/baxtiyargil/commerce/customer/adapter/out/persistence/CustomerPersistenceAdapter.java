package az.baxtiyargil.commerce.customer.adapter.out.persistence;

import az.baxtiyargil.commerce.customer.application.port.out.CheckCustomerExistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerPersistenceAdapter implements CheckCustomerExistencePort {

    private final CustomerJpaRepository customerJpaRepository;

    @Override
    public Boolean exists(Long id) {
        return customerJpaRepository.existsById(id);
    }
}
