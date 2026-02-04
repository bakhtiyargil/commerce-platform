package az.baxtiyargil.commerce.order.adapter.out.persistence;

import az.baxtiyargil.commerce.order.adapter.out.persistence.mapper.OrderPersistenceMapper;
import az.baxtiyargil.commerce.order.application.port.out.PersistOrderPort;
import az.baxtiyargil.commerce.order.domain.model.Order;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements PersistOrderPort {

    private final OrderRepository orderRepository;
    private final OrderPersistenceMapper persistenceMapper;

    @Transactional
    @Override
    public Order persist(Order order) {
        OrderJpaEntity orderJpaEntity = persistenceMapper.toOrderJpaEntity(order);
        orderJpaEntity = orderRepository.save(orderJpaEntity);
        return persistenceMapper.toOrder(orderJpaEntity);
    }

}
