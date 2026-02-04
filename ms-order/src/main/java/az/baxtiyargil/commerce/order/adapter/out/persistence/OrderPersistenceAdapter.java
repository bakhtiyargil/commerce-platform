package az.baxtiyargil.commerce.order.adapter.out.persistence;

import az.baxtiyargil.commerce.order.adapter.mapper.OrderMapper;
import az.baxtiyargil.commerce.order.application.port.out.PersistOrderPort;
import az.baxtiyargil.commerce.order.domain.model.Order;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements PersistOrderPort {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public Order persist(Order order) {
        var orderJpaEntity = orderMapper.toOrderJpaEntity(order);
        orderJpaEntity = orderRepository.save(orderJpaEntity);
        return orderMapper.toOrder(orderJpaEntity);
    }
}
