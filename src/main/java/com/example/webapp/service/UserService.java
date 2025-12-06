package com.example.webapp.service;

import com.example.webapp.model.dto.UserDTO;

public interface UserService {
    UserDTO create(UserDTO dto);
    UserDTO getById(Long id);
}
