package az.baxtiyargil.commerce.gateway.client.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthContextRequest(@NotBlank String accessToken, String correlationId) {
}
