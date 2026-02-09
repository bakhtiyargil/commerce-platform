package az.baxtiyargil.commerce.product.adapter.in.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductPricesWebResponse(List<ProductPrice> productPrices) {

    public record ProductPrice(Long id, BigDecimal unitPrice) {
    }

}
