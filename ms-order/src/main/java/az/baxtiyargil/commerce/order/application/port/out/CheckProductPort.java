package az.baxtiyargil.commerce.order.application.port.out;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public interface CheckProductPort {

    Map<Long, BigDecimal> findProductsWithPrices(Set<Long> productIds);

}
