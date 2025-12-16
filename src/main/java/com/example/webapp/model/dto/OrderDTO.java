package com.example.webapp.model.dto;

import java.util.Set;

public record OrderDTO(
        String status,
        Long customerId,
        Set<Long> productIds
) {}