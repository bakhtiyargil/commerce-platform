package az.baxtiyargil.commerce.auth.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record AuthContextRequest(@NotBlank @JsonProperty("access_token") String accessToken,
                                 @JsonProperty("correlation_id") String correlationId) {
}
