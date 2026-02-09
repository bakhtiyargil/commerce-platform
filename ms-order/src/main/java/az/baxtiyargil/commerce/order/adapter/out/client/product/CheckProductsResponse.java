package az.baxtiyargil.commerce.order.adapter.out.client.product;

import java.util.Set;

public record CheckProductsResponse(Set<Long> existingIds) {
}
