package az.baxtiyargil.commerce.auth.adapter.out.client;

import feign.form.FormProperty;
import lombok.Data;

@Data
public class ClientCredentialsRequest {

    @FormProperty("grant_type")
    String grantType;

    @FormProperty("client_id")
    String clientId;

    @FormProperty("client_secret")
    String clientSecret;

}
