package az.baxtiyargil.commerce.product.application.port.in;

import az.baxtiyargil.commerce.product.domain.model.Product;
import java.util.List;
import java.util.Set;

public interface GetProductPricesQuery {

    List<Product> execute(Set<Long> id);

}
