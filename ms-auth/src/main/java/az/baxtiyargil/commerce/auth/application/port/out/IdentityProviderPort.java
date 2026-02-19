package az.baxtiyargil.commerce.auth.application.port.out;

import az.baxtiyargil.commerce.auth.application.port.in.RegisterCommand;
import az.baxtiyargil.commerce.auth.domain.AuthToken;
import java.util.Set;

public interface IdentityProviderPort {

    String registerUser(RegisterCommand command, String defaultRole);

    AuthToken login(String username, String password);

    AuthToken refresh(String refreshToken);

    void logout(String refreshToken);

    boolean usernameExists(String username);

    boolean emailExists(String email);

    TokenIntrospectionResult introspect(String accessToken);

    record TokenIntrospectionResult(boolean active,
                                    String userId,
                                    String email,
                                    String username,
                                    Set<String> roles,
                                    Set<String> permissions) {
        public static TokenIntrospectionResult inactive() {
            return new TokenIntrospectionResult(false, null, null, null, Set.of(), Set.of());
        }
    }
}
