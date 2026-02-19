package az.baxtiyargil.commerce.auth.application.usecase;

import az.baxtiyargil.commerce.auth.application.exception.AuthErrorCodes;
import az.baxtiyargil.commerce.auth.application.exception.AuthException;
import az.baxtiyargil.commerce.auth.application.port.out.IdentityProviderPort;
import az.baxtiyargil.commerce.auth.domain.AuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final IdentityProviderPort identityProvider;

    public AuthToken execute(String username, String password) {
        try {
            return identityProvider.login(username, password);
        } catch (Exception e) {
            // Never leak whether username or password is wrong
            throw new AuthException(AuthErrorCodes.INVALID_CREDENTIALS, "Invalid username or password");
        }
    }
}
