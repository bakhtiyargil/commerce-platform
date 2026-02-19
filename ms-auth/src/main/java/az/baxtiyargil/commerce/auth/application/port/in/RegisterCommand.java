package az.baxtiyargil.commerce.auth.application.port.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterCommand(
        @NotBlank @Email
        String email,
        @NotBlank @Size(min = 3, max = 50)
        String username,
        @NotBlank @Size(min = 8)
        String password,
        String firstName,
        String lastName) {
}
