package az.baxtiyargil.commerce.customer.adapter.in;

import az.baxtiyargil.commerce.customer.adapter.in.dto.CheckStoreWebResponse;
import az.baxtiyargil.commerce.customer.application.usecase.CheckStoreUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/stores")
public class StoreController {

    private final CheckStoreUseCase checkStoreUseCase;

    @GetMapping("/{storeId}/exists")
    public ResponseEntity<CheckStoreWebResponse> existsById(@PathVariable Long storeId) {
        Boolean result = checkStoreUseCase.exists(storeId);
        return ResponseEntity.ok(new CheckStoreWebResponse(result));
    }

}
