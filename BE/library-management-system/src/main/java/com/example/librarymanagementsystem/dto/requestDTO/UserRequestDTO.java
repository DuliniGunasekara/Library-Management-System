package com.example.librarymanagementsystem.dto.requestDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDTO {
    private String username;
    private String password;
}
