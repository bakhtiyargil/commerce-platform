package az.baxtiyargil.commerce.lib.error.component;

import az.baxtiyargil.commerce.lib.error.ApplicationException;
import az.baxtiyargil.commerce.lib.error.AuthException;
import az.baxtiyargil.commerce.lib.error.ErrorResponse;
import az.baxtiyargil.commerce.lib.error.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorMessageResolver errorMessageResolver;

    public GlobalExceptionHandler(ErrorMessageResolver errorMessageResolver) {
        this.errorMessageResolver = errorMessageResolver;
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {
        var errId = UUID.randomUUID().toString();
        String message = errorMessageResolver.getMessage(ex.getErrorCode(), ex.getArgs());
        log("Validation error", errId, ex.getErrorCode().status(), ex);
        var response = buildErrorResponse(errId, ex.getErrorCode().asString(), message, ex.getErrorCode().status().value());
        return ResponseEntity.status(ex.getErrorCode().status()).body(response);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException ex) {
        var errId = UUID.randomUUID().toString();
        String message = errorMessageResolver.getMessage(ex.getErrorCode(), ex.getArgs());
        log("Application error", errId, ex.getErrorCode().status(), ex);
        var response = buildErrorResponse(errId, ex.getErrorCode().asString(), message, ex.getErrorCode().status().value());
        return ResponseEntity.status(ex.getErrorCode().status()).body(response);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Object> handleAuthException(AuthException ex) {
        var errId = UUID.randomUUID().toString();
        String message = errorMessageResolver.getMessage(ex.getErrorCode(), ex.getArgs());
        log("Authentication error", errId, ex.getErrorCode().status(), ex);
        var response = buildErrorResponse(errId, ex.getErrorCode().asString(), message, ex.getErrorCode().status().value());
        return ResponseEntity.status(ex.getErrorCode().status()).body(response);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        String errId = UUID.randomUUID().toString();
        ErrorResponse response = new ErrorResponse(
                errId,
                "VALIDATION ERROR",
                "VALIDATION FAILED",
                HttpStatus.BAD_REQUEST.value()
        );
        log("Method argument not valid error", errId, HttpStatus.BAD_REQUEST, ex);

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> response.addProperty(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ));
        return ResponseEntity.badRequest().body(response);
    }

    private ErrorResponse buildErrorResponse(String id, String errorCode, String message, Integer status) {
        return new ErrorResponse(
                id,
                errorCode,
                message,
                status
        );
    }

    private void log(String title, String errId, HttpStatus status, Exception ex) {
        log.error("{}, errId: {}, errStatus: {}, errMsg: {}, errCause: {}",
                title, errId, status.value(), ex.getMessage(), getCauseMessage(ex));
    }

    private static String getCauseMessage(Exception ex) {
        return ex.getCause() != null ? ex.getCause().getMessage() : "NoCause";
    }

}
