package az.baxtiyargil.commerce.lib.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String asString();

    HttpStatus status();

    String message();

    RetryPolicy retryPolicy();

}
