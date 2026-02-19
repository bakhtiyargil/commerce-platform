package az.baxtiyargil.commerce.auth.adapter.in;

import az.baxtiyargil.commerce.auth.adapter.in.dto.AuthContextRequest;
import az.baxtiyargil.commerce.auth.adapter.in.dto.AuthContextResponse;
import az.baxtiyargil.commerce.auth.application.usecase.BuildAuthContextUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/internal")
@RequiredArgsConstructor
public class InternalAuthController {

    private final BuildAuthContextUseCase authContextUseCase;

    @PostMapping("/auth-context")
    public ResponseEntity<AuthContextResponse> buildAuthContext(@Valid @RequestBody AuthContextRequest req) {
        String signedContext = authContextUseCase.execute(req.accessToken(), req.correlationId());
        return ResponseEntity.ok(new AuthContextResponse(signedContext, req.correlationId()));
    }
}
