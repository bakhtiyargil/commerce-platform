package az.baxtiyargil.commerce.auth.adapter.out.client;

import feign.form.FormProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IntrospectionRequest {

    @FormProperty("client_id")
    String clientId;

    @FormProperty("client_secret")
    String clientSecret;

    @FormProperty("token")
    String token;

}
