package az.baxtiyargil.commerce.order.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import static az.baxtiyargil.commerce.order.adapter.out.persistence.PersistenceConstants.SERIAL_VERSION_UID;

@Data
@Embeddable
public class OrderItemId implements Serializable {

    @Serial
    private static final long serialVersionUID = SERIAL_VERSION_UID;

    @Column(name = "line_item_id", unique = true, nullable = false, updatable = false)
    private Integer lineItemId;
    private Long orderId;

}
