package com.example.webapp.service;

import com.example.webapp.model.dto.OrderDTO;

public interface OrderService {
    OrderDTO getById(Long id);
    OrderDTO create(OrderDTO dto);
    OrderDTO update(Long id, OrderDTO dto);
    void delete(Long id);
}
