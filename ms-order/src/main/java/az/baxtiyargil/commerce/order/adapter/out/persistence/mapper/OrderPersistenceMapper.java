package az.baxtiyargil.commerce.order.adapter.out.persistence.mapper;

import az.baxtiyargil.commerce.order.adapter.out.persistence.OrderJpaEntity;
import az.baxtiyargil.commerce.order.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring"
)
public interface OrderPersistenceMapper {

    OrderJpaEntity toOrderJpaEntity(Order order);

    Order toOrder(OrderJpaEntity orderJpaEntity);

}
