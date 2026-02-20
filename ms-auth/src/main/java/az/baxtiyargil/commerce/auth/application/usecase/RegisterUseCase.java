package az.baxtiyargil.commerce.auth.application.usecase;

import az.baxtiyargil.commerce.auth.domain.Roles;
import az.baxtiyargil.commerce.auth.domain.error.AuthErrorCodes;
import az.baxtiyargil.commerce.auth.application.port.in.RegisterCommand;
import az.baxtiyargil.commerce.auth.application.port.out.IdentityProviderPort;
import az.baxtiyargil.commerce.lib.error.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUseCase {

    private final IdentityProviderPort identityProvider;

    public String execute(RegisterCommand command) {
        if (identityProvider.usernameExists(command.username())) {
            throw new AuthException(AuthErrorCodes.USERNAME_TAKEN);
        }
        if (identityProvider.emailExists(command.email())) {
            throw new AuthException(AuthErrorCodes.EMAIL_TAKEN);
        }

        return identityProvider.registerUser(command, Roles.CUSTOMER.asString());
    }
}
