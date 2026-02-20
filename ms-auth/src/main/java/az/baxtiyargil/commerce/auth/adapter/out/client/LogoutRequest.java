package az.baxtiyargil.commerce.auth.adapter.out.client;

import feign.form.FormProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogoutRequest {

    @FormProperty("client_id")
    private String clientId;

    @FormProperty("client_secret")
    private String clientSecret;

    @FormProperty("refresh_token")
    private String refreshToken;

}
