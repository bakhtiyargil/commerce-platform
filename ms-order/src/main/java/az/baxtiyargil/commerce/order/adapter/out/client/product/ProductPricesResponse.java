package az.baxtiyargil.commerce.order.adapter.out.client.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductPricesResponse {

    List<ProductPrice> productPrices;

    @Getter
    @AllArgsConstructor
    public static class ProductPrice {
        private Long id;
        private BigDecimal unitPrice;
    }
}
