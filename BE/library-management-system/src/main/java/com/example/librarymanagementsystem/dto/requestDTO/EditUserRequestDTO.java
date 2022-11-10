package com.example.librarymanagementsystem.dto.requestDTO;

import com.example.librarymanagementsystem.constants.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditUserRequestDTO extends UserRequestDTO {
//    private String username;
    private String userRole;

}
