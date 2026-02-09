package az.baxtiyargil.commerce.customer.application.port.in;

public interface CheckCustomerExistenceQuery {

    Boolean exists(Long id);

}
