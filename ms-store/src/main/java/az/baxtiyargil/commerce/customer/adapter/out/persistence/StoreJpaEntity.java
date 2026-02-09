package az.baxtiyargil.commerce.customer.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.Hibernate;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import static az.baxtiyargil.commerce.customer.adapter.out.persistence.PersistenceConstants.SERIAL_VERSION_UID;

@Data
@Entity
@Table(name = "STORES")
public class StoreJpaEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = SERIAL_VERSION_UID;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "store_id_seq_gen")
    @SequenceGenerator(name = "store_id_seq_gen", sequenceName = "ISEQ$$_72304", allocationSize = 1)
    @Column(name = "store_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(name = "store_name", nullable = false)
    private String name;

    @Column(name = "web_address")
    private String webAddress;

    @Column(name = "physical_address")
    private String physicalAddress;

    @Column(name = "latitude")
    private Integer latitude;

    @Column(name = "longitude")
    private Integer longitude;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_mime_type")
    private String logoMimeType;

    @Column(name = "logo_filename")
    private String logoFilename;

    @Column(name = "logo_charset")
    private String logoCharset;

    @Column(name = "logo_last_updated")
    private LocalDateTime logoLastUpdated;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        StoreJpaEntity storeJpaEntity = (StoreJpaEntity) o;
        return getId() != null && Objects.equals(getId(), storeJpaEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
