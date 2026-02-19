package az.baxtiyargil.commerce.auth.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(@NotBlank @Email @JsonProperty("email") String email,
                              @NotBlank @Size(min = 3, max = 50) @JsonProperty("username") String username,
                              @NotBlank @Size(min = 8) @JsonProperty("password") String password,
                              @JsonProperty("first_name") String firstName,
                              @JsonProperty("last_name") String lastName) {
}
