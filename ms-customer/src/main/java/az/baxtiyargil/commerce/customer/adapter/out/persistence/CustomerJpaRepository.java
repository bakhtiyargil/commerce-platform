package az.baxtiyargil.commerce.customer.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, Long> {
}
