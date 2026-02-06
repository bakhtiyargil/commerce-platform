package az.baxtiyargil.commerce.product.application.port.out;

import java.util.Set;

public interface FindExistingProductsPort {

    Set<Long> findExisting(Set<Long> ids);

}
