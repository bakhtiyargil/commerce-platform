package az.baxtiyargil.commerce.customer.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {

    Long id;
    String name;
    String webAddress;
    String physicalAddress;
    Long latitude;
    Long longitude;
    byte[] logo;
    String logoMimeType;
    String logoFileName;
    String logoCharset;
    LocalDateTime logoLastUpdated;

}
