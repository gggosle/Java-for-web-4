package com.example.webapp.model.mapper;

import com.example.webapp.model.Product;
import com.example.webapp.model.dto.ProductDTO;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDto(Product product);
    Product toEntity(ProductDTO dto);
}
