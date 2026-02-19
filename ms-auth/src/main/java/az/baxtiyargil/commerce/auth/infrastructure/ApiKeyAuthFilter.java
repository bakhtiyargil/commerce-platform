package az.baxtiyargil.commerce.auth.infrastructure;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-Gateway-Api-Key";
    private static final String INTERNAL_PATH  = "/v1/api/internal/";

    private final String expectedApiKey;

    public ApiKeyAuthFilter(@Value("${auth.gateway.api-key}") String expectedApiKey) {
        if (expectedApiKey == null || expectedApiKey.isBlank()) {
            throw new IllegalStateException("auth.gateway.api-key must be set");
        }
        this.expectedApiKey = expectedApiKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        if (!request.getRequestURI().startsWith(INTERNAL_PATH)) {
            chain.doFilter(request, response);
            return;
        }

        String providedKey = request.getHeader(API_KEY_HEADER);
        if (!constantTimeEquals(expectedApiKey, providedKey)) {
            log.warn("Rejected /internal request — invalid or missing API key. URI={}",
                    request.getRequestURI());
            sendUnauthorized(response);
            return;
        }
        chain.doFilter(request, response);
    }

    private void sendUnauthorized(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write("""
                {"error":"UNAUTHORIZED","message":"Invalid or missing gateway API key"}
                """);
    }

    /**
     * Constant-time compare — prevents timing oracle.
     */
    private boolean constantTimeEquals(String a, String b) {
        if (b == null) {
            return false;
        }
        if (a.length() != b.length()) {
            return false;
        }
        int diff = 0;
        for (int i = 0; i < a.length(); i++) {
            diff |= (a.charAt(i) ^ b.charAt(i));
        }
        return diff == 0;
    }
}
