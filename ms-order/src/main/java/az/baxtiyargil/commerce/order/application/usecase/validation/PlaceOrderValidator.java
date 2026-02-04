package az.baxtiyargil.commerce.order.application.usecase.validation;

import az.baxtiyargil.commerce.order.application.port.in.dto.PlaceOrderRequest;

public class PlaceOrderValidator implements Validator<PlaceOrderRequest> {

    @Override
    public void validate(PlaceOrderRequest command) {

    }

    @Override
    public Validator<PlaceOrderRequest> join(Validator<PlaceOrderRequest> validator) {
        return null;
    }

}
