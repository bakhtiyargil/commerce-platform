package az.baxtiyargil.commerce.order.adapter.out.client.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class GetProductRequest {

    Long id;
    Set<Long> ids;

    public static GetProductRequest withIds(Set<Long> ids) {
        return new GetProductRequest(null, ids);
    }

}
