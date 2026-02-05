package az.baxtiyargil.commerce.product.application.port.in;

import java.util.Set;

public interface FindExistingProductsQuery {

    Set<Long> execute(Set<Long> ids);

}
