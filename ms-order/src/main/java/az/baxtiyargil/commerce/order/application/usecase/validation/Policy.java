package az.baxtiyargil.commerce.order.application.usecase.validation;

public interface Policy<T> {

    ValidationResult validate(T command);

}
