package az.baxtiyargil.commerce.product.application.port.in;

import az.baxtiyargil.commerce.product.domain.model.Product;

public interface GetProductQuery {

    Product execute(Long id);

}
