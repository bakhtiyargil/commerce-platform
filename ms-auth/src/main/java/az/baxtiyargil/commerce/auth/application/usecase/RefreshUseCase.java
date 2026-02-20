package az.baxtiyargil.commerce.auth.application.usecase;

import az.baxtiyargil.commerce.auth.domain.error.AuthErrorCodes;
import az.baxtiyargil.commerce.auth.application.port.out.IdentityProviderPort;
import az.baxtiyargil.commerce.auth.domain.AuthToken;
import az.baxtiyargil.commerce.lib.error.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshUseCase {

    private final IdentityProviderPort identityProvider;

    public AuthToken execute(String refreshToken) {
        try {
            return identityProvider.refresh(refreshToken);
        } catch (Exception e) {
            throw new AuthException(AuthErrorCodes.INVALID_REFRESH_TOKEN);
        }
    }
}
