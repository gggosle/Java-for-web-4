package com.example.webapp.model.dto;

import java.util.Set;

public record OrderDTO(
        Long id,
        String status,
        String orderDate,
        Long customerId,
        Set<Long> productIds
) {}