package com.example.webapp.service;

import com.example.webapp.model.dto.CustomerDTO;

public interface CustomerService {
    CustomerDTO getById(Long id);
    CustomerDTO create(CustomerDTO dto);
    CustomerDTO update(Long id, CustomerDTO dto);
    void delete(Long id);
}