package az.baxtiyargil.commerce.customer.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.Hibernate;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import static az.baxtiyargil.commerce.customer.adapter.out.persistence.PersistenceConstants.SERIAL_VERSION_UID;

@Data
@Entity
@Table(name = "CUSTOMERS")
public class CustomerJpaEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = SERIAL_VERSION_UID;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq_gen")
    @SequenceGenerator(name = "customer_id_seq_gen", sequenceName = "ISEQ$$_72302", allocationSize = 1)
    @Column(name = "customer_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        CustomerJpaEntity customerJpaEntity = (CustomerJpaEntity) o;
        return getId() != null && Objects.equals(getId(), customerJpaEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
