package az.baxtiyargil.commerce.product.application.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Collections;
import java.util.Set;

@Data
@AllArgsConstructor
public class CheckProductsResult {

    private Set<Long> existingIds;
    private Set<Long> missingIds;

    public static CheckProductsResult empty() {
        return new CheckProductsResult(Collections.emptySet(), Collections.emptySet());
    }

    public boolean allExist() {
        return missingIds.isEmpty();
    }

    public boolean anyMissing() {
        return !missingIds.isEmpty();
    }

}
