package az.baxtiyargil.commerce.auth.application.usecase;

import az.baxtiyargil.commerce.auth.application.port.out.IdentityProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutUseCase {

    private final IdentityProviderPort identityProvider;

    public void execute(String refreshToken) {
        identityProvider.logout(refreshToken);
    }
}
