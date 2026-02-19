package az.baxtiyargil.commerce.auth.adapter.in.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String username, @NotBlank String password) {
}
