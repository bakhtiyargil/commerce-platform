package az.baxtiyargil.commerce.store.adapter.in;

import az.baxtiyargil.commerce.store.adapter.in.dto.StoreExistenceWebResponse;
import az.baxtiyargil.commerce.store.application.usecase.CheckStoreUseCase;
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

    @GetMapping("/{id}/existence")
    public ResponseEntity<StoreExistenceWebResponse> checkExistenceById(@PathVariable Long id) {
        var result = checkStoreUseCase.exists(id);
        return ResponseEntity.ok(new StoreExistenceWebResponse(result));
    }

}
