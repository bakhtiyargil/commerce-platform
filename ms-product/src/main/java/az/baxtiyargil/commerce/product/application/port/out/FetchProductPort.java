package az.baxtiyargil.commerce.product.application.port.out;

import az.baxtiyargil.commerce.product.domain.model.Product;
import java.util.List;
import java.util.Set;

public interface FetchProductPort {

    Product fetch(Long id);

    List<Product> fetchAll(Set<Long> ids);

}
