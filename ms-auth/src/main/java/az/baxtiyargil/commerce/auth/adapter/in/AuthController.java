package az.baxtiyargil.commerce.auth.adapter.in;

import az.baxtiyargil.commerce.auth.adapter.in.dto.LoginRequest;
import az.baxtiyargil.commerce.auth.adapter.in.dto.LogoutRequest;
import az.baxtiyargil.commerce.auth.adapter.in.dto.RefreshRequest;
import az.baxtiyargil.commerce.auth.adapter.in.dto.RegisterRequest;
import az.baxtiyargil.commerce.auth.adapter.in.dto.RegisterResponse;
import az.baxtiyargil.commerce.auth.adapter.in.dto.TokenResponse;
import az.baxtiyargil.commerce.auth.adapter.in.mapper.AuthMapper;
import az.baxtiyargil.commerce.auth.application.port.in.RegisterCommand;
import az.baxtiyargil.commerce.auth.application.usecase.LoginUseCase;
import az.baxtiyargil.commerce.auth.application.usecase.LogoutUseCase;
import az.baxtiyargil.commerce.auth.application.usecase.RefreshUseCase;
import az.baxtiyargil.commerce.auth.application.usecase.RegisterUseCase;
import az.baxtiyargil.commerce.auth.domain.AuthToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthMapper authMapper;
    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;
    private final RefreshUseCase refreshUseCase;
    private final RegisterUseCase registerUseCase;

    /**
     * POST /auth/register
     * <p>
     * Create a new user account.
     * Returns 201 with the Keycloak userId.
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest req) {
        String userId = registerUseCase.execute(new RegisterCommand(
                req.email(), req.username(), req.password(),
                req.firstName(), req.lastName()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(userId));
    }

    /**
     * POST /auth/login
     * <p>
     * Authenticate with username and password.
     * Returns access_token + refresh_token pair.
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest req) {
        AuthToken token = loginUseCase.execute(req.username(), req.password());
        return ResponseEntity.ok(authMapper.toTokenResponse(token));
    }

    /**
     * POST /auth/refresh
     * <p>
     * Exchange a refresh_token for a new token pair.
     * Old refresh_token is rotated by Keycloak automatically.
     */
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody RefreshRequest req) {
        AuthToken token = refreshUseCase.execute(req.refreshToken());
        return ResponseEntity.ok(authMapper.toTokenResponse(token));
    }

    /**
     * POST /auth/logout
     * <p>
     * Revoke the refresh_token (back-channel logout).
     * The access_token will expire naturally (Keycloak short TTL recommended).
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequest req) {
        logoutUseCase.execute(req.refreshToken());
        return ResponseEntity.noContent().build();
    }

}
