package com.example.webapp.service.impl;

import com.example.webapp.model.Customer;
import com.example.webapp.model.dto.CustomerDTO;
import com.example.webapp.model.mapper.CustomerMapper;
import com.example.webapp.repository.CustomerRepository;
import com.example.webapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepo;
    private final CustomerMapper mapper;

    @Override
    public CustomerDTO getById(Long id) {
        return mapper.toDto(
                customerRepo.findById(id).orElseThrow()
        );
    }

    @Override
    public CustomerDTO create(CustomerDTO dto) {
        Customer saved = customerRepo.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Override
    public CustomerDTO update(Long id, CustomerDTO dto) {
        Customer existing = customerRepo.findById(id).orElseThrow();

        existing.setFullName(dto.fullName());
        existing.setEmail(dto.email());

        return mapper.toDto(customerRepo.save(existing));
    }

    @Override
    public void delete(Long id) {
        customerRepo.deleteById(id);
    }
}