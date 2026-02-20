package az.baxtiyargil.commerce.auth.adapter.out.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.util.List;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IntrospectionResponse {

    @JsonProperty("active")
    Boolean active;

    @JsonProperty("sub")
    String sub;

    @JsonProperty("email")
    String email;

    @JsonProperty("preferred_username")
    String preferredUsername;

    @JsonProperty("realm_access")
    Map<String, Object> realmAccess;

    @JsonProperty("permissions")
    List<String> permissions;

    @JsonProperty("exp")
    Long exp;

    @JsonProperty("iat")
    Long iat;

    @JsonProperty("client_id")
    String clientId;

    @JsonProperty("username")
    String username;

    @JsonProperty("token_type")
    String tokenType;

    @JsonProperty("scope")
    String scope;

}
