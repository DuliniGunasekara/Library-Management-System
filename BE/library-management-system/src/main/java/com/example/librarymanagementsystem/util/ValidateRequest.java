package com.example.librarymanagementsystem.util;

import com.example.librarymanagementsystem.dto.requestDTO.LoginRequestDTO;
import com.example.librarymanagementsystem.dto.requestDTO.RegisterRequestDTO;
import org.springframework.util.StringUtils;

public class ValidateRequest {

    public static boolean validateLoginRequestDTO(final LoginRequestDTO loginRequestDTO){
        if(StringUtils.hasLength(loginRequestDTO.getUsername())
                && StringUtils.hasLength(loginRequestDTO.getPassword())){
            return true;
        }
        return false;
    }

    public static boolean validateRegisterRequestDTO(final RegisterRequestDTO registerRequestDTO){
        if(StringUtils.hasLength(registerRequestDTO.getUsername())
        && StringUtils.hasLength(registerRequestDTO.getPassword())
        && StringUtils.hasLength(registerRequestDTO.getUserRole())){
            return true;
        }
        return false;
    }
}
