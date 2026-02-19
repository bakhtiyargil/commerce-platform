package az.baxtiyargil.commerce.auth.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(@NotBlank @JsonProperty("refresh_token") String refreshToken) {
}
