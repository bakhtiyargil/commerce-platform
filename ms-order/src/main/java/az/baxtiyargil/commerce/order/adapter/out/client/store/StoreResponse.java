package az.baxtiyargil.commerce.order.adapter.out.client.store;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StoreResponse {

    Long id;
    String name;
    String webAddress;
    String physicalAddress;

}
