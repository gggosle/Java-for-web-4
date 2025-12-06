package com.example.webapp.model.dto;

public record CustomerDTO(
        Long id,
        String fullName,
        String email,
        Long userId
) {}