package az.baxtiyargil.commerce.auth.adapter.in.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthContextRequest(@NotBlank String accessToken, String correlationId) {
}
