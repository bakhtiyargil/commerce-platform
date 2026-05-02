package az.baxtiyargil.commerce.product.adapter.in.dto;

import java.util.Set;

public record ProductsExistenceWebResponse(Set<Long> existingIds) {
}
