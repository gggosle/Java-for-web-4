package com.example.webapp.service;

import com.example.webapp.model.dto.ProductDTO;

public interface ProductService {
    ProductDTO getById(Long id);
    ProductDTO create(ProductDTO dto);
    ProductDTO update(Long id, ProductDTO dto);
    void delete(Long id);
}
