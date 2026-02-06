package az.baxtiyargil.commerce.order.adapter.out.client.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;

@Data
@AllArgsConstructor
public class CheckProductsRequest {

    private Set<Long> productIds;

}
