package com.example.webapp.model.mapper;

import com.example.webapp.model.Order;
import com.example.webapp.model.dto.OrderDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;


@Mapper(componentModel = "spring", imports = {
LocalDateTime.class
        })
public interface OrderMapper {

    @Mapping(target = "productIds", expression = "java(order.getProducts().stream().map(p -> p.getId()).collect(java.util.stream.Collectors.toSet()))")
    @Mapping(target = "customerId", source = "customer.id")
    OrderDTO toDto(Order order);

    @Mapping(target = "products", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "orderDate", expression = "java(LocalDateTime.now())")
    Order toEntity(OrderDTO dto);
}