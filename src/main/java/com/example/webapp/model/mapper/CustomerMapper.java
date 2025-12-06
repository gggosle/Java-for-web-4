package com.example.webapp.model.mapper;

import com.example.webapp.model.Customer;
import com.example.webapp.model.dto.CustomerDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO toDto(Customer customer);

    @Mapping(target = "orders", ignore = true)
    Customer toEntity(CustomerDTO dto);
}
