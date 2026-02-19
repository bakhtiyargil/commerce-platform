package az.baxtiyargil.commerce.auth.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthContextResponse(@JsonProperty("auth_context") String authContext,      // signed HMAC token
                                  @JsonProperty("correlation_id") String correlationId) {
}
