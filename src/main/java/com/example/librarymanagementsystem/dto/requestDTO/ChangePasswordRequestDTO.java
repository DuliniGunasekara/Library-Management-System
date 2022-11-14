package com.example.librarymanagementsystem.dto.requestDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordRequestDTO{
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

}
