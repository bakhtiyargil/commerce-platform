package az.baxtiyargil.commerce.product.adapter.in;

import az.baxtiyargil.commerce.product.adapter.in.dto.CheckProductsRequest;
import az.baxtiyargil.commerce.product.adapter.in.dto.CheckProductsWebResponse;
import az.baxtiyargil.commerce.product.adapter.in.dto.ProductWebResponse;
import az.baxtiyargil.commerce.product.adapter.in.mapper.ProductWebMapper;
import az.baxtiyargil.commerce.product.application.port.in.FindExistingProductsQuery;
import az.baxtiyargil.commerce.product.application.port.in.CheckProductsExistenceQuery;
import az.baxtiyargil.commerce.product.application.usecase.dto.CheckProductsResult;
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

@RestController
@RequestMapping("/v1/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final CheckProductsExistenceQuery getProductQuery;
    private final ProductWebMapper productWebMapper;
    private final FindExistingProductsQuery findExistingProductsQuery;

    @GetMapping("/{productId}")
    public ProductWebResponse getById(@PathVariable Long productId) {
        Product product = getProductQuery.execute(productId);
        return productWebMapper.toProductWebResponse(product);
    }

    @PostMapping("/check-existence")
    public ResponseEntity<CheckProductsWebResponse> existing(@Valid @RequestBody CheckProductsRequest request) {
        CheckProductsResult result = findExistingProductsQuery.execute(request.getProductIds());
        return ResponseEntity.ok(new CheckProductsWebResponse(result.getExistingIds(), result.getMissingIds()));
    }

}
