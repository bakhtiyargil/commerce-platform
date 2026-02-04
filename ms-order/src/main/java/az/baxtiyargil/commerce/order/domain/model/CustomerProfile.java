package az.baxtiyargil.commerce.order.domain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerProfile {

    Long id;
    String name;
    String email;

}
