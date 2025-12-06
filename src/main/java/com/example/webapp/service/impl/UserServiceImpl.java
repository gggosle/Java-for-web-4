package com.example.webapp.service.impl;

import com.example.webapp.model.dto.UserDTO;
import com.example.webapp.model.mapper.UserMapper;
import com.example.webapp.repository.UserRepository;
import com.example.webapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final UserMapper mapper;

    @Override
    public UserDTO create(UserDTO dto) {
        return mapper.toDto(repo.save(mapper.toEntity(dto)));
    }

    @Override
    public UserDTO getById(Long id) {
        return mapper.toDto(repo.findById(id).orElseThrow());
    }
}