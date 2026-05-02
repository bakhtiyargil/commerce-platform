package az.baxtiyargil.commerce.product.adapter.in;

import az.baxtiyargil.commerce.product.adapter.in.dto.CheckProductsRequest;
import az.baxtiyargil.commerce.product.adapter.in.dto.ProductsExistenceWebResponse;
import az.baxtiyargil.commerce.product.adapter.in.dto.GetPricesRequest;
import az.baxtiyargil.commerce.product.adapter.in.dto.ProductPricesWebResponse;
import az.baxtiyargil.commerce.product.adapter.in.dto.ProductWebResponse;
import az.baxtiyargil.commerce.product.adapter.in.mapper.ProductWebMapper;
import az.baxtiyargil.commerce.product.application.port.in.FindExistingProductsQuery;
import az.baxtiyargil.commerce.product.application.port.in.GetProductPricesQuery;
import az.baxtiyargil.commerce.product.application.port.in.GetProductQuery;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {

    private final GetProductQuery getProductQuery;
    private final ProductWebMapper productWebMapper;
    private final GetProductPricesQuery getProductPricesQuery;
    private final FindExistingProductsQuery findExistingProductsQuery;

    @GetMapping("/{id}")
    public ResponseEntity<ProductWebResponse> getById(@PathVariable Long id) {
        var product = getProductQuery.execute(id);
        return ResponseEntity.ok(productWebMapper.toProductWebResponse(product));
    }

    @PostMapping("/existence")
    public ResponseEntity<ProductsExistenceWebResponse> checkExistence(@Valid @RequestBody CheckProductsRequest request) {
        var result = findExistingProductsQuery.execute(request.getProductIds());
        return ResponseEntity.ok(new ProductsExistenceWebResponse(result));
    }

    @PostMapping("/prices")
    public ResponseEntity<ProductPricesWebResponse> fetchPrices(@Valid @RequestBody GetPricesRequest request) {
        var result = getProductPricesQuery.execute(request.getProductIds());
        return ResponseEntity.ok(productWebMapper.toProductPricesWebResponse(result));
    }

}
