package com.example.librarymanagementsystem.dto.requestDTO;

import com.example.librarymanagementsystem.constants.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequestDTO extends MemberRequestDTO{
    private String userRole;
}
