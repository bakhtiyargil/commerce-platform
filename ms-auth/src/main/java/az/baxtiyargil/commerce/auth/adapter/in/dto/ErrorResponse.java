package az.baxtiyargil.commerce.auth.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponse(@JsonProperty("error") String error,
                            @JsonProperty("message") String message,
                            @JsonProperty("status") int status) {
}
