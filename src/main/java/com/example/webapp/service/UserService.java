package com.example.webapp.service;

import com.example.webapp.model.dto.UserCreateDTO;
import com.example.webapp.model.dto.UserDTO;

public interface UserService {
    UserDTO create(UserCreateDTO dto);
    UserDTO getById(Long id);
}
