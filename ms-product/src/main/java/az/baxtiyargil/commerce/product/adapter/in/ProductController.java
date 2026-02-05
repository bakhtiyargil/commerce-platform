package az.baxtiyargil.commerce.product.adapter.in;

import az.baxtiyargil.commerce.product.adapter.in.dto.ProductWebResponse;
import az.baxtiyargil.commerce.product.adapter.in.mapper.ProductWebMapper;
import az.baxtiyargil.commerce.product.application.port.in.GetProductQuery;
import az.baxtiyargil.commerce.product.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final GetProductQuery productQuery;
    private final ProductWebMapper productWebMapper;

    @GetMapping("/{productId}")
    public ProductWebResponse getById(@PathVariable Long productId) {
        Product product = productQuery.execute(productId);
        return productWebMapper.toProductWebResponse(product);
    }

}
