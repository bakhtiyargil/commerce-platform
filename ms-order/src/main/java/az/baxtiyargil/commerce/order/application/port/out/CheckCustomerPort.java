package az.baxtiyargil.commerce.order.application.port.out;

public interface CheckCustomerPort {

    boolean exists(Long customerId);

}
