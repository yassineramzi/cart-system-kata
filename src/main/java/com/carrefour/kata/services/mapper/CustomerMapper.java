package com.carrefour.kata.services.mapper;

import com.carrefour.kata.domains.Customer;
import com.carrefour.kata.services.dto.CustomerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public non-sealed interface CustomerMapper extends EntityMapper<CustomerDto, Customer> {

    @Override
    CustomerDto toDto(Customer city);

    @Override
    Customer toEntity(CustomerDto cityDto);

    default Customer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }
}