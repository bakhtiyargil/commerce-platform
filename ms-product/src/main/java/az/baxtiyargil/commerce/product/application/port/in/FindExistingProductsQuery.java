package az.baxtiyargil.commerce.product.application.port.in;

import az.baxtiyargil.commerce.product.application.usecase.dto.CheckProductsResult;
import java.util.Set;

public interface FindExistingProductsQuery {

    CheckProductsResult execute(Set<Long> ids);

}
