package az.baxtiyargil.commerce.auth.application.usecase;

import az.baxtiyargil.commerce.auth.application.exception.AuthErrorCodes;
import az.baxtiyargil.commerce.auth.application.exception.AuthException;
import az.baxtiyargil.commerce.auth.application.port.in.RegisterCommand;
import az.baxtiyargil.commerce.auth.application.port.out.IdentityProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUseCase {

    private final IdentityProviderPort identityProvider;

    public String execute(RegisterCommand command) {

        if (identityProvider.usernameExists(command.username())) {
            throw new AuthException(AuthErrorCodes.USERNAME_TAKEN,
                    "Username already taken: " + command.username());
        }
        if (identityProvider.emailExists(command.email())) {
            throw new AuthException(AuthErrorCodes.EMAIL_TAKEN,
                    "Email already registered: " + command.email());
        }

        // All new users start as CUSTOMER; promote via admin console or separate API
        return identityProvider.registerUser(command, "CUSTOMER");
    }
}
