package az.baxtiyargil.commerce.product.adapter.in.dto;

import java.util.Set;

public record CheckProductsWebResponse(Set<Long> existingIds) {
}
