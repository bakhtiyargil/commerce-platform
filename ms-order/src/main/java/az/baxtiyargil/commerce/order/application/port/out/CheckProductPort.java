package az.baxtiyargil.commerce.order.application.port.out;

import java.util.Set;

public interface CheckProductPort {

    Set<Long> whichExistsAmongThese(Set<Long> productIds);

}
