package az.baxtiyargil.commerce.order.application.usecase.validation;

import lombok.Getter;

public class ValidationResult {

    private Result result;
    private String message;

    @Getter
    private boolean success;
    private Throwable error;

    public static ValidationResult success() {
        ValidationResult validationResult = new ValidationResult();
        validationResult.success = true;
        validationResult.result = Result.SUCCESS;
        return validationResult;
    }

    public static ValidationResult failure(Throwable error) {
        ValidationResult validationResult = new ValidationResult();
        validationResult.success = false;
        validationResult.result = Result.FAILURE;
        validationResult.error = error;
        return validationResult;
    }

    public boolean isFailure() {
        return !success;
    }

    public enum Result {
        SUCCESS,
        FAILURE
    }
}
