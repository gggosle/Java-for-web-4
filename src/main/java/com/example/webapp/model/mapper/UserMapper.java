package com.example.webapp.model.mapper;

import com.example.webapp.model.AppUser;
import com.example.webapp.model.dto.UserCreateDTO;
import com.example.webapp.model.dto.UserDTO;

import com.example.webapp.utils.mapper.UserMapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = {
UserMapperUtil.class
        },
imports = {
LocalDateTime.class
        })
public interface UserMapper {

    UserDTO toDto(AppUser user);

    @Mapping(target = "passwordHash", qualifiedByName = {"UserMapperUtil", "getHashedPassword"}, source = "password")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    AppUser toEntity(UserCreateDTO dto);


}
