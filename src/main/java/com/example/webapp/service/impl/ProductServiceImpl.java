package com.example.webapp.service.impl;

import com.example.webapp.model.Product;
import com.example.webapp.model.dto.ProductDTO;
import com.example.webapp.model.mapper.ProductMapper;
import com.example.webapp.repository.ProductRepository;
import com.example.webapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;
    private final ProductMapper mapper;

    @Override
    public ProductDTO getById(Long id) {
        return mapper.toDto(repo.findById(id).orElseThrow());
    }

    @Override
    public ProductDTO create(ProductDTO dto) {
        return mapper.toDto(repo.save(mapper.toEntity(dto)));
    }

    @Override
    public ProductDTO update(Long id, ProductDTO dto) {
        Product existing = repo.findById(id).orElseThrow();

        existing.setName(dto.name());
        existing.setDescription(dto.description());
        existing.setPrice(dto.price());

        return mapper.toDto(repo.save(existing));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
