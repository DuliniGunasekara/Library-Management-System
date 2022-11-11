package com.example.librarymanagementsystem.util;

import com.example.librarymanagementsystem.dto.requestDTO.*;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class ValidateRequest {

    public static boolean validateUserRequestDTO(final MemberRequestDTO userRequestDTO){
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

    public static boolean validateEditUserRequestDTO(final EditMemberRequestDTO editMemberRequestDTO){
        if(StringUtils.hasLength(editMemberRequestDTO.getUsername()) &&
                StringUtils.hasLength(editMemberRequestDTO.getUserRole())){
            return true;
        }
        return false;
    }

    public static boolean validateBookRequestDTO(final BookRequestDTO bookRequestDTO){
        if(StringUtils.hasLength(bookRequestDTO.getIsbnNumber()) &&
                StringUtils.hasLength(bookRequestDTO.getName())){
            return true;
        }
        return false;
    }

    public static boolean validateIssueRequestDTO(final IssueRequestDTO issueRequestDTO){
        if(!issueRequestDTO.getBookList().isEmpty()
        && StringUtils.hasLength(issueRequestDTO.getMemberUsername())
        && StringUtils.hasLength(issueRequestDTO.getDueDate())
        && compareDates(issueRequestDTO.getDueDate())){
            return true;
        }
        return false;
    }

    public static boolean compareDates(final String date){
        if(UtilMethods.convertStringToDateFormat(date).compareTo(LocalDate.now()) > 0){
            return true;
        }
        return false;
    }
}
