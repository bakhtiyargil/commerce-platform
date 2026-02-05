package az.baxtiyargil.commerce.product.application.port.out;

import az.baxtiyargil.commerce.product.domain.model.Product;

public interface FetchProductPort {

    Product fetch(Long id);

}
