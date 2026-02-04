package az.baxtiyargil.commerce.order.adapter.out.client.customer;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {

    Long id;
    String name;
    String email;

}
