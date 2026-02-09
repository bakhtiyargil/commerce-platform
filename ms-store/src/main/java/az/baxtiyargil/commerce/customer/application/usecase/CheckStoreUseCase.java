package az.baxtiyargil.commerce.customer.application.usecase;

import az.baxtiyargil.commerce.customer.application.port.in.CheckStoreExistenceQuery;
import az.baxtiyargil.commerce.customer.application.port.out.CheckStoreExistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckStoreUseCase implements CheckStoreExistenceQuery {

    private final CheckStoreExistencePort checkStoreExistencePort;

    @Override
    public Boolean exists(Long id) {
        return checkStoreExistencePort.exists(id);
    }
}
