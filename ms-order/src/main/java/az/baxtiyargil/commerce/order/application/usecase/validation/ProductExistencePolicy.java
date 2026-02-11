package az.baxtiyargil.commerce.order.application.usecase.validation;

import az.baxtiyargil.commerce.order.application.port.in.dto.PlaceOrderRequest;
import az.baxtiyargil.commerce.order.application.port.out.CheckProductPort;
import az.baxtiyargil.commerce.order.domain.error.exception.ApplicationErrorCodes;
import az.baxtiyargil.commerce.order.domain.error.exception.ApplicationException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Order(3)
public class ProductExistencePolicy implements Policy<PlaceOrderRequest> {

    private final CheckProductPort checkProductPort;

    public ProductExistencePolicy(CheckProductPort checkProductPort) {
        this.checkProductPort = checkProductPort;
    }

    @Override
    public ValidationResult validate(PlaceOrderRequest command) {
        Map<Long, BigDecimal> itemsWithPrices = command.getOrderItems()
                .stream()
                .collect(Collectors.toMap(
                        PlaceOrderRequest.AddOrderItemRequest::getProductId,
                        PlaceOrderRequest.AddOrderItemRequest::getUnitPrice)
                );
        Map<Long, BigDecimal> existing = checkProductPort.findProductsWithPrices(itemsWithPrices.keySet());
        if (!itemsWithPrices.keySet().containsAll(existing.keySet())) {
            throw new ApplicationException(ApplicationErrorCodes.PRODUCT_NOT_FOUND, itemsWithPrices.keySet());
        }

        itemsWithPrices.forEach((key, value) -> existing.computeIfPresent(key, (k, v) -> {
            if (value.compareTo(v) != 0) {
                throw new ApplicationException(ApplicationErrorCodes.PRODUCT_PRICE_MISMATCH, k);
            }
            return v;
        }));
        return ValidationResult.success();
    }

}
