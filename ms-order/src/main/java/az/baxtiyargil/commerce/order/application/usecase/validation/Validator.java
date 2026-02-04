package az.baxtiyargil.commerce.order.application.usecase.validation;

public interface Validator<T> {

    void validate(T command);

    Validator<T> join(Validator<T> validator);

}
