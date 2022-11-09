package com.example.librarymanagementsystem.dto.responseDTO.mapper;

import com.example.librarymanagementsystem.domain.AppUser;
import com.example.librarymanagementsystem.dto.responseDTO.LoginResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.RegisterResponseDTO;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {

    public RegisterResponseDTO mapAppUserToRegisterResponseDTO(final AppUser appUser){

        RegisterResponseDTO registerResponseDTO = new RegisterResponseDTO();
        registerResponseDTO.setId(appUser.getId());
        registerResponseDTO.setUsername(appUser.getUsername());
        return registerResponseDTO;
    }

    public LoginResponseDTO mapToLoginResponseDTO(final String jwt){
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setJwtToken(jwt);
        return loginResponseDTO;
    }
}
