package az.baxtiyargil.commerce.product.adapter.out.persistence;

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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import static az.baxtiyargil.commerce.product.adapter.out.persistence.PersistenceConstants.SERIAL_VERSION_UID;

@Data
@Entity
@Table(name = "PRODUCTS")
public class ProductJpaEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = SERIAL_VERSION_UID;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq_gen")
    @SequenceGenerator(name = "product_id_seq_gen", sequenceName = "ISEQ$$_72308", allocationSize = 1)
    @Column(name = "product_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "product_details")
    private ProductDetails status;

    @Column(name = "product_image")
    private String image;

    @Column(name = "image_mime_type")
    private String imageMimeType;

    @Column(name = "image_filename")
    private String imageFilename;

    @Column(name = "image_charset")
    private String imageCharset;

    @Column(name = "image_last_updated")
    private LocalDateTime imageLastUpdated;

    public record ProductDetails(String colour,
                                 String gender,
                                 String brand,
                                 String description,
                                 Integer[] sizes,
                                 String[] comments
    ) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        ProductJpaEntity order = (ProductJpaEntity) o;
        return getId() != null && Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
