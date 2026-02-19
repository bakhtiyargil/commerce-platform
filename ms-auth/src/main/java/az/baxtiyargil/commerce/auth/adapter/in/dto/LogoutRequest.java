package az.baxtiyargil.commerce.auth.adapter.in.dto;

import jakarta.validation.constraints.NotBlank;

public record LogoutRequest(@NotBlank String refreshToken) {
}
