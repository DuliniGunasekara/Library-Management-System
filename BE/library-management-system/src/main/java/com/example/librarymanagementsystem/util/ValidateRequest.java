package com.example.librarymanagementsystem.util;

import com.example.librarymanagementsystem.dto.requestDTO.*;
import org.springframework.util.StringUtils;

public class ValidateRequest {

    public static boolean validateUserRequestDTO(final UserRequestDTO userRequestDTO){
        if(StringUtils.hasLength(userRequestDTO.getUsername())
                && StringUtils.hasLength(userRequestDTO.getPassword())){
            return true;
        }
        return false;
    }

    public static boolean validateRegisterRequestDTO(final RegisterRequestDTO registerRequestDTO){
        if(validateUserRequestDTO(registerRequestDTO)
        && StringUtils.hasLength(registerRequestDTO.getUserRole())){
            return true;
        }
        return false;
    }

    public static boolean validateChangePasswordRequestDTO(final ChangePasswordRequestDTO changePasswordRequestDTO){
        if(StringUtils.hasLength(changePasswordRequestDTO.getNewPassword())
                && StringUtils.hasLength(changePasswordRequestDTO.getConfirmPassword())
                && changePasswordRequestDTO.getNewPassword().equals(changePasswordRequestDTO.getConfirmPassword())){
                return true;
        }
        return false;
    }

    public static boolean validateEditUserRequestDTO(final EditUserRequestDTO editUserRequestDTO){
        if(StringUtils.hasLength(editUserRequestDTO.getUsername()) &&
                StringUtils.hasLength(editUserRequestDTO.getUserRole())){
            return true;
        }
        return false;
    }
}
