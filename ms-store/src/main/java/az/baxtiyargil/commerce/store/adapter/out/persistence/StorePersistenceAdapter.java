package az.baxtiyargil.commerce.store.adapter.out.persistence;

import az.baxtiyargil.commerce.store.application.port.out.CheckStoreExistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorePersistenceAdapter implements CheckStoreExistencePort {

    private final StoreJpaRepository productRepository;

    @Override
    public Boolean exists(Long id) {
        return productRepository.existsById(id);
    }
}
