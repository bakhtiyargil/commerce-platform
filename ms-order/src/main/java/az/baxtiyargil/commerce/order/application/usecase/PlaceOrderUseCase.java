package az.baxtiyargil.commerce.order.application.usecase;

import az.baxtiyargil.commerce.order.application.port.in.PlaceOrderCommand;
import az.baxtiyargil.commerce.order.application.port.in.dto.PlaceOrderRequest;
import az.baxtiyargil.commerce.order.application.port.out.CheckCustomerPort;
import az.baxtiyargil.commerce.order.application.port.out.CheckProductPort;
import az.baxtiyargil.commerce.order.application.port.out.CheckStorePort;
import az.baxtiyargil.commerce.order.application.port.out.PersistOrderPort;
import az.baxtiyargil.commerce.order.application.usecase.mapper.OrderMapper;
import az.baxtiyargil.commerce.order.domain.error.exception.ApplicationErrorCodes;
import az.baxtiyargil.commerce.order.domain.error.exception.ApplicationException;
import az.baxtiyargil.commerce.order.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PlaceOrderUseCase implements PlaceOrderCommand {

    private final OrderMapper orderMapper;
    private final CheckStorePort checkStorePort;
    private final PersistOrderPort persistOrderPort;
    private final CheckProductPort checkProductPort;
    private final CheckCustomerPort checkCustomerPort;

    @Override
    public Order execute(PlaceOrderRequest request) {
        validateInParallel(request);
        var order = orderMapper.toOrder(request);
        return persistOrderPort.persist(order);
    }

    private void validateInParallel(PlaceOrderRequest request) throws ApplicationException {
        Set<Long> itemIds = request.getOrderItems()
                .stream()
                .map(PlaceOrderRequest.AddOrderItemRequest::getProductId)
                .collect(Collectors.toSet());

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            scope.fork(() -> {
                if (!checkCustomerPort.isValid(request.getCustomerId())) {
                    throw new ApplicationException(ApplicationErrorCodes.CUSTOMER_NOT_FOUND, request.getCustomerId());
                }
                return null;
            });

            scope.fork(() -> {
                if (!checkStorePort.isValid(request.getStoreId())) {
                    throw new ApplicationException(ApplicationErrorCodes.STORE_NOT_FOUND, request.getStoreId());
                }
                return null;
            });

            scope.fork(() -> {
                Set<Long> missing = checkProductPort.whichExistsAmongThese(itemIds);
                if (!missing.isEmpty()) {
                    throw new ApplicationException(ApplicationErrorCodes.PRODUCT_NOT_FOUND, missing);
                }
                return null;
            });

            scope.join();
            scope.throwIfFailed();
        } catch (InterruptedException | ExecutionException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
