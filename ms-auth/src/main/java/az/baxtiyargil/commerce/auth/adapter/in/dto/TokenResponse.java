package az.baxtiyargil.commerce.auth.adapter.in.dto;

public record TokenResponse(String accessToken,
                            String refreshToken,
                            long expiresIn,
                            long refreshExpiresIn,
                            String tokenType) {
}
