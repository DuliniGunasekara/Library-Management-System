package com.example.librarymanagementsystem.dto.requestDTO.mapper;

import com.example.librarymanagementsystem.constants.UserRole;
import com.example.librarymanagementsystem.domain.AppUser;
import com.example.librarymanagementsystem.dto.requestDTO.RegisterRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserRequestMapper {

    PasswordEncoder passwordEncoder;
    public AppUser mapRegisterRequestDTOtoAppUser(final RegisterRequestDTO registerRequestDTO){
        AppUser appUser = new AppUser(UserRole.valueOf(registerRequestDTO.getUserRole()));
        appUser.setUsername(registerRequestDTO.getUsername());
        appUser.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        return appUser;
    }
}
