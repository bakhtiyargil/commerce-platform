package az.baxtiyargil.commerce.customer.adapter.in;

import az.baxtiyargil.commerce.customer.adapter.in.dto.CheckCustomerWebResponse;
import az.baxtiyargil.commerce.customer.application.usecase.CheckCustomerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/customers")
public class CustomerController {

    private final CheckCustomerUseCase checkCustomerUseCase;

    @GetMapping("/{customerId}/exists")
    public ResponseEntity<CheckCustomerWebResponse> existsById(@PathVariable Long customerId) {
        Boolean result = checkCustomerUseCase.exists(customerId);
        return ResponseEntity.ok(new CheckCustomerWebResponse(result));
    }

}
