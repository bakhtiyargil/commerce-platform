package az.baxtiyargil.commerce.product.adapter.in;

import az.baxtiyargil.commerce.product.adapter.in.dto.ExistingProductsWebResponse;
import az.baxtiyargil.commerce.product.adapter.in.dto.ProductWebResponse;
import az.baxtiyargil.commerce.product.adapter.in.mapper.ProductWebMapper;
import az.baxtiyargil.commerce.product.application.port.in.FindExistingProductsQuery;
import az.baxtiyargil.commerce.product.application.port.in.GetProductQuery;
import az.baxtiyargil.commerce.product.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final GetProductQuery getProductQuery;
    private final ProductWebMapper productWebMapper;
    private final FindExistingProductsQuery findExistingProductsQuery;

    @GetMapping("/{productId}")
    public ProductWebResponse getById(@PathVariable Long productId) {
        Product product = getProductQuery.execute(productId);
        return productWebMapper.toProductWebResponse(product);
    }

    @GetMapping("/existing")
    public ExistingProductsWebResponse existing(@RequestParam Set<Long> ids) {
        Set<Long> productIds = findExistingProductsQuery.execute(ids);
        return new ExistingProductsWebResponse(productIds);
    }
}
