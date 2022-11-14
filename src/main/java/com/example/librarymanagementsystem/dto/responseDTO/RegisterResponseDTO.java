package com.example.librarymanagementsystem.dto.responseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponseDTO {
    private String username;
    private String password;
    private String userRole;
}
