package com.example.webapp.utils.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Named("UserMapperUtil")
@Component
@RequiredArgsConstructor
public class UserMapperUtil {

    private final PasswordEncoder passwordEncoder;

    @Named("getHashedPassword")
    public String getHashedPassword(String password){
        return  passwordEncoder.encode(password);
    }
}