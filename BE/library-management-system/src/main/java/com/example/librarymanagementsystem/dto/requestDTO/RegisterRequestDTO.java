package com.example.librarymanagementsystem.dto.requestDTO;

import com.example.librarymanagementsystem.constants.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String userRole;
}
