package az.baxtiyargil.commerce.order.application.port.out;

import az.baxtiyargil.commerce.order.domain.model.Order;

public interface PersistOrderPort {

    Order persist(Order order);

}
