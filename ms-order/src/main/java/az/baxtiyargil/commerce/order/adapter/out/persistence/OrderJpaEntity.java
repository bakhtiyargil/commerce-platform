package az.baxtiyargil.commerce.order.adapter.out.persistence;

import az.baxtiyargil.commerce.order.domain.model.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.Hibernate;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import static az.baxtiyargil.commerce.order.adapter.out.persistence.PersistenceConstants.SERIAL_VERSION_UID;

@Data
@Entity
@Table(name = "ORDERS")
public class OrderJpaEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = SERIAL_VERSION_UID;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq_gen")
    @SequenceGenerator(name = "order_id_seq_gen", sequenceName = "ISEQ$$_72314", allocationSize = 1)
    @Column(name = "order_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(name = "order_tms", nullable = false)
    private LocalDateTime orderedAt;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus status;

    @NotNull
    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @PrePersist
    public void prePersist() {
        this.orderedAt = LocalDateTime.now();
        this.status = OrderStatus.OPEN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        OrderJpaEntity order = (OrderJpaEntity) o;
        return getId() != null && Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
