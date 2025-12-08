package com.example.webapp.controller;

import com.example.webapp.model.dto.UserCreateDTO;
import com.example.webapp.model.dto.UserDTO;
import com.example.webapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    public UserDTO get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public UserDTO create(@RequestBody UserCreateDTO dto) {
        return service.create(dto);
    }
}
