package az.baxtiyargil.commerce.order.adapter.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class PlaceOrderRequest {

    @NotNull
    private Long storeId;

    @NotNull
    private Long customerId;

    @NotEmpty
    @Size(max = 100)
    private List<@NotNull @Valid AddOrderItemRequest> orderItems;

}
