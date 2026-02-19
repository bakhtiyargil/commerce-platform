package az.baxtiyargil.commerce.auth.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterResponse(@JsonProperty("user_id") String userId,
                               @JsonProperty("message") String message) {
}
