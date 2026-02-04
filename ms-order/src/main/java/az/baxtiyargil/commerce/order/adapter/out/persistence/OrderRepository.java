package az.baxtiyargil.commerce.order.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderJpaEntity, Long> {
}
