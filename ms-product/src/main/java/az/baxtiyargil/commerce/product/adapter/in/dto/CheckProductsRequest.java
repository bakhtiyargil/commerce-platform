package az.baxtiyargil.commerce.product.adapter.in.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Set;

@Data
public class CheckProductsRequest {

    @NotEmpty
    @Size(min = 1, max = 100)
    private Set<Long> productIds;

}
