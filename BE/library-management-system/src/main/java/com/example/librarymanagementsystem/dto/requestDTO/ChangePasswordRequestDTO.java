package com.example.librarymanagementsystem.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequestDTO {
    private String username;
    private String newPassword;
    private String confirmPassword;
}
