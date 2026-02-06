package az.baxtiyargil.commerce.product.adapter.in.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckProductsWebResponse {

    final Set<Long> existingIds;

    final Set<Long> missingIds;

    final boolean allExist;

    public CheckProductsWebResponse(Set<Long> existingIds, Set<Long> missingIds) {
        this.existingIds = existingIds;
        this.missingIds = missingIds;
        this.allExist = missingIds.isEmpty();
    }
}
