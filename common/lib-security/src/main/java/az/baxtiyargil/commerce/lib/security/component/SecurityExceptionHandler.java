package az.baxtiyargil.commerce.lib.security.component;

import az.baxtiyargil.commerce.lib.security.ServiceAuthContext;
import az.baxtiyargil.commerce.lib.security.ServiceAuthContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class SecurityExceptionHandler {

    private final ObjectMapper objectMapper;

    public void handleAccessDenied(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exception
    ) throws IOException {
        String userId = ServiceAuthContextHolder.getOptional()
                .map(ServiceAuthContext::userId)
                .orElse("anonymous");

        String correlationId = ServiceAuthContextHolder.getOptional()
                .map(ServiceAuthContext::correlationId)
                .orElse(UUID.randomUUID().toString());

        log.debug("Access denied — userId={}, uri={}, correlationId={}, reason={}",
                userId, request.getRequestURI(), correlationId, exception.getMessage());

        sendErrorResponse(
                response,
                HttpStatus.FORBIDDEN,
                "ACCESS_DENIED",
                "You do not have permission to access this resource",
                request.getRequestURI(),
                correlationId
        );
    }

    public void handleAuthenticationError(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {

        String correlationId = request.getHeader("X-Correlation-Id");
        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString();
        }

        log.warn("Authentication failed — uri={}, correlationId={}, reason={}",
                request.getRequestURI(), correlationId, exception.getMessage());

        sendErrorResponse(
                response,
                HttpStatus.UNAUTHORIZED,
                "UNAUTHORIZED",
                "Authentication required. Please provide valid credentials.",
                request.getRequestURI(),
                correlationId
        );
    }

    private void sendErrorResponse(HttpServletResponse response,
                                   HttpStatus status,
                                   String errorCode,
                                   String message,
                                   String path,
                                   String correlationId) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("id", correlationId);
        errorBody.put("code", errorCode);
        errorBody.put("message", message);
        errorBody.put("status", status.value());
        errorBody.put("timestamp", Instant.now().toString());
        errorBody.put("path", path);
        errorBody.put("properties", Collections.emptyList());
        response.getWriter().write(objectMapper.writeValueAsString(errorBody));
    }

}
