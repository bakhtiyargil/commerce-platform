package az.baxtiyargil.commerce.order.adapter.out.client.mapper;

import az.baxtiyargil.commerce.order.adapter.out.client.customer.CustomerResponse;
import az.baxtiyargil.commerce.order.domain.model.CustomerProfile;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring"
)
public interface CustomerMapper {

    CustomerProfile toCustomerProfile(CustomerResponse customerDto);

}
