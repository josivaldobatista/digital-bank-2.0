package com.jfb.customer.mapper;

import com.jfb.customer.dto.AddressRecord;
import com.jfb.customer.dto.CustomerCreateRequest;
import com.jfb.customer.dto.CustomerResponse;
import com.jfb.customer.dto.CustomerUpdateRequest;
import com.jfb.customer.model.Address;
import com.jfb.customer.model.Customer;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerCode", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deactivatedAt", ignore = true)
    @Mapping(target = "deactivatedReason", ignore = true)
    Customer toEntity(CustomerCreateRequest request);

    CustomerResponse toResponse(Customer customer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "customerCode", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deactivatedAt", ignore = true)
    @Mapping(target = "deactivatedReason", ignore = true)
    void updateCustomerFromRequest(CustomerUpdateRequest request, @MappingTarget Customer customer);

    Address toEntity(AddressRecord addressRecord);

    AddressRecord toRecord(Address address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAddressFromRecord(AddressRecord record, @MappingTarget Address address);
}
