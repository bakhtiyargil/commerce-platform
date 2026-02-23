package az.baxtiyargil.commerce.lib.error.component;

import az.baxtiyargil.commerce.lib.error.ApplicationException;
import az.baxtiyargil.commerce.lib.error.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class RestExceptionResponseWriteHandler {

    private final ObjectMapper objectMapper;
    private final ErrorMessageResolver errorMessageResolver;

    public void call(HttpServletRequest req,
                     HttpServletResponse res,
                     ApplicationException ex) throws IOException {
        var errId = UUID.randomUUID().toString();
        String message = errorMessageResolver.getMessage(ex.getErrorCode(), ex.getArgs());
        log.error("Application error, errId: {}, errStatus: {}, errMsg: {}, errCause: {}",
                errId, ex.getErrorCode().status().value(), ex.getMessage(), getCauseMessage(ex));
        var errorResponse = new ErrorResponse(
                errId,
                ex.getErrorCode().asString(),
                message,
                ex.getErrorCode().status().value()
        );
        final var jsonBytes = toJsonBytes(errorResponse);
        res.setStatus(ex.getErrorCode().status().value());
        write(res, jsonBytes);
    }

    private static void write(HttpServletResponse res, byte[] jsonBytes) throws IOException {
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setHeader("Cache-Control", "no-cache");
        res.getOutputStream().write(jsonBytes);
        res.getOutputStream().flush();
    }

    private static String getCauseMessage(Exception ex) {
        return ex.getCause() != null ? ex.getCause().getMessage() : "NoCause";
    }

    private byte[] toJsonBytes(ErrorResponse errorResponse) throws IOException {
        try {
            return objectMapper.writeValueAsBytes(errorResponse);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not convert object to json", e);
        }
    }
}
