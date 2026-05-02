package az.baxtiyargil.commerce.store.application.usecase;

import az.baxtiyargil.commerce.store.application.port.in.CheckStoreExistenceQuery;
import az.baxtiyargil.commerce.store.application.port.out.CheckStoreExistencePort;
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
