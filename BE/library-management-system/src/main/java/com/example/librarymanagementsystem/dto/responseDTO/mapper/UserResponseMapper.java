package com.example.librarymanagementsystem.dto.responseDTO.mapper;

import com.example.librarymanagementsystem.domain.AppUser;
import com.example.librarymanagementsystem.dto.responseDTO.EditUserResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.LoginResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {

    public UserResponseDTO mapAppUserToUserResponseDTO(final AppUser appUser){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(appUser.getId());
        userResponseDTO.setUsername(appUser.getUsername());
        return userResponseDTO;
    }

    public LoginResponseDTO mapToLoginResponseDTO(final String jwt){
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setJwtToken(jwt);
        return loginResponseDTO;
    }

    public EditUserResponseDTO mapAppUserToEditUserResponseDTO(final AppUser appUser){
        EditUserResponseDTO editUserResponseDTO = new EditUserResponseDTO();
        editUserResponseDTO.setId(appUser.getId());
        editUserResponseDTO.setUsername(appUser.getUsername());
        editUserResponseDTO.setUserRole(appUser.getUserRole().toString());
        return editUserResponseDTO;
    }
}
