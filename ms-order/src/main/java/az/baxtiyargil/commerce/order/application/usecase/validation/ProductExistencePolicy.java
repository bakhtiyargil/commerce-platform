package az.baxtiyargil.commerce.order.application.usecase.validation;

import az.baxtiyargil.commerce.lib.error.ApplicationException;
import az.baxtiyargil.commerce.order.application.port.in.dto.PlaceOrderRequest;
import az.baxtiyargil.commerce.order.application.port.out.CheckProductPort;
import az.baxtiyargil.commerce.order.domain.error.EntityNotFoundErrorCodes;
import az.baxtiyargil.commerce.order.domain.error.ValidationErrorCodes;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
                        PlaceOrderRequest.AddOrderItemRequest::getUnitPrice,
                        (_, price) -> price)
                );
        Map<Long, BigDecimal> existing = checkProductPort.findProductsWithPrices(itemsWithPrices.keySet());
        if (existing.isEmpty() || !existing.keySet().containsAll(itemsWithPrices.keySet())) {
            Set<Long> difference = new HashSet<>(itemsWithPrices.keySet());
            difference.removeAll(existing.keySet());
            throw new ApplicationException(EntityNotFoundErrorCodes.PRODUCT_NOT_FOUND, difference);
        }

        itemsWithPrices.forEach((key, value) -> existing.computeIfPresent(key, (k, v) -> {
            if (value.compareTo(v) != 0) {
                throw new ApplicationException(ValidationErrorCodes.PRODUCT_PRICE_MISMATCH, k);
            }
            return v;
        }));
        return ValidationResult.success();
    }

}
