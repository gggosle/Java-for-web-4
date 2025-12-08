package com.example.webapp.model.mapper;

import com.example.webapp.model.AppUser;
import com.example.webapp.model.dto.UserCreateDTO;
import com.example.webapp.model.dto.UserDTO;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(AppUser user);
    AppUser toEntity(UserCreateDTO dto, String password);
}
