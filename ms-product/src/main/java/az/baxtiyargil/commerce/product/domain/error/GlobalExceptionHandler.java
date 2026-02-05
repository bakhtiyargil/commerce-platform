package az.baxtiyargil.commerce.product.domain.error;

import az.baxtiyargil.commerce.product.domain.error.exception.ApplicationException;
import az.baxtiyargil.commerce.product.domain.error.exception.ErrorCode;
import az.baxtiyargil.commerce.product.domain.error.exception.ValidationErrorCodes;
import az.baxtiyargil.commerce.product.domain.error.exception.ValidationException;
import az.baxtiyargil.commerce.product.infrastructure.component.MessageResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.NoSuchMessageException;
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

    private final MessageResolver messageResolver;

    public GlobalExceptionHandler(MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {
        var errId = UUID.randomUUID().toString();
        String message = resolveMessage(ex.getErrorCode(), ex.getArgs());
        log("Validation error", errId, ex.getErrorCode().status(), ex);
        var response = buildErrorResponse(errId, ex.getErrorCode().name(), message, ex.getErrorCode().status().value());
        return ResponseEntity.status(ex.getErrorCode().status()).body(response);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException ex) {
        var errId = UUID.randomUUID().toString();
        String message = resolveMessage(ex.getErrorCode(), ex.getArgs());
        log("Application error", errId, ex.getErrorCode().status(), ex);
        var response = buildErrorResponse(errId, ex.getErrorCode().name(), message, ex.getErrorCode().status().value());
        return ResponseEntity.status(ex.getErrorCode().status()).body(response);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        String errId = UUID.randomUUID().toString();
        ErrorResponse response = new ErrorResponse(
                errId,
                ValidationErrorCodes.VALIDATION_ERROR.asString(),
                resolveMessage(ValidationErrorCodes.VALIDATION_ERROR, new Object[]{}),
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

    private String resolveMessage(ErrorCode code, Object[] args) {
        try {
            return messageResolver.getMessage(code.message(), args);
        } catch (NoSuchMessageException exception) {
            return code.message();
        }
    }

    private void log(String title, String errId, HttpStatus status, Exception ex) {
        log.error("{}, errId: {}, errStatus: {}, errMsg: {}, errCause: {}",
                title, errId, status.value(), ex.getMessage(), getCauseMessage(ex));
    }

    private static String getCauseMessage(Exception ex) {
        return ex.getCause() != null ? ex.getCause().getMessage() : "NoCause";
    }
}
