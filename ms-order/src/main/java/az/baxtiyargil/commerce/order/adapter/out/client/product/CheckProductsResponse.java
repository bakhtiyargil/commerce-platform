package az.baxtiyargil.commerce.order.adapter.out.client.product;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckProductsResponse {

    Set<Long> existingIds;
    Set<Long> missingIds;

}
