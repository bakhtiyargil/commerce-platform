package az.baxtiyargil.commerce.auth.adapter.out.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenResponse {

    @JsonProperty("access_token")
    String accessToken;

    @JsonProperty("refresh_token")
    String refreshToken;

    @JsonProperty("expires_in")
    Long expiresIn;

    @JsonProperty("refresh_expires_in")
    Long refreshExpiresIn;

    @JsonProperty("token_type")
    String tokenType;

    @JsonProperty("id_token")
    String idToken;

    @JsonProperty("not-before-policy")
    Long notBeforePolicy;

    @JsonProperty("session_state")
    String sessionState;

    @JsonProperty("scope")
    String scope;

}
