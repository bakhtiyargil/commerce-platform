package az.baxtiyargil.commerce.order.domain.error.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String asString();

    HttpStatus status();

    String message();

    RetryPolicy retryPolicy();

}
