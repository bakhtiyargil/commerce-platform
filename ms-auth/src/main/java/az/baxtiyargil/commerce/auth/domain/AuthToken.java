package az.baxtiyargil.commerce.auth.domain;

public record AuthToken(String accessToken,
                        String refreshToken,
                        long expiresIn,
                        long refreshExpiresIn,
                        String tokenType) {

    private static final String BEARER = "Bearer ";

    public static AuthToken of(String accessToken,
                               String refreshToken,
                               long expiresIn,
                               long refreshExpiresIn) {
        return new AuthToken(accessToken, refreshToken, expiresIn, refreshExpiresIn, BEARER);
    }

}
