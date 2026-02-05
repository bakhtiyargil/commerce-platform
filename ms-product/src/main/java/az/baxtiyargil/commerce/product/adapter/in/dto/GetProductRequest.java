package az.baxtiyargil.commerce.product.adapter.in.dto;

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

}
