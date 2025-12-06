package com.example.webapp.model.dto;

public record UserDTO(
        Long id,
        String username,
        String createdAt
) {}