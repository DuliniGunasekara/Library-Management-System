package com.example.librarymanagementsystem.dto.responseDTO;

import com.example.librarymanagementsystem.constants.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditUserResponseDTO extends UserResponseDTO {
    private String userRole;
}
