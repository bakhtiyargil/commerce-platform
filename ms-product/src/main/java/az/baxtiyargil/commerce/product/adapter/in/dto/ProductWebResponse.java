package az.baxtiyargil.commerce.product.adapter.in.dto;

import az.baxtiyargil.commerce.product.domain.model.ProductDetails;
import java.math.BigDecimal;

public record ProductWebResponse(Long id, String name, BigDecimal unitPrice, ProductDetails details) {
}
