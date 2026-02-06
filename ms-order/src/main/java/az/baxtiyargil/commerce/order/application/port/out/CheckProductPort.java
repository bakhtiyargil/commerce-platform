package az.baxtiyargil.commerce.order.application.port.out;

import java.util.Set;

public interface CheckProductPort {

    Set<Long> whichMissingAmongThese(Set<Long> productIds);

}
