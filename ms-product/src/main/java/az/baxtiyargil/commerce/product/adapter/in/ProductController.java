package az.baxtiyargil.commerce.product.adapter.in;

import az.baxtiyargil.commerce.product.adapter.in.dto.CheckProductsRequest;
import az.baxtiyargil.commerce.product.adapter.in.dto.CheckProductsWebResponse;
import az.baxtiyargil.commerce.product.adapter.in.dto.GetPricesRequest;
import az.baxtiyargil.commerce.product.adapter.in.dto.ProductPricesWebResponse;
import az.baxtiyargil.commerce.product.adapter.in.dto.ProductWebResponse;
import az.baxtiyargil.commerce.product.adapter.in.mapper.ProductWebMapper;
import az.baxtiyargil.commerce.product.application.port.in.FindExistingProductsQuery;
import az.baxtiyargil.commerce.product.application.port.in.GetProductPricesQuery;
import az.baxtiyargil.commerce.product.application.port.in.GetProductQuery;
import az.baxtiyargil.commerce.product.domain.model.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/products")
public class ProductController {

    private final GetProductQuery getProductQuery;
    private final ProductWebMapper productWebMapper;
    private final GetProductPricesQuery getProductPricesQuery;
    private final FindExistingProductsQuery findExistingProductsQuery;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductWebResponse> getById(@PathVariable Long productId) {
        Product product = getProductQuery.execute(productId);
        return ResponseEntity.ok(productWebMapper.toProductWebResponse(product));
    }

    @PostMapping("/check-existence")
    public ResponseEntity<CheckProductsWebResponse> existing(@Valid @RequestBody CheckProductsRequest request) {
        Set<Long> result = findExistingProductsQuery.execute(request.getProductIds());
        return ResponseEntity.ok(new CheckProductsWebResponse(result));
    }

    @PostMapping("/prices")
    public ResponseEntity<ProductPricesWebResponse> getProductPrices(@Valid @RequestBody GetPricesRequest request) {
        List<Product> result = getProductPricesQuery.execute(request.getProductIds());
        return ResponseEntity.ok(productWebMapper.toProductPricesWebResponse(result));
    }

}
