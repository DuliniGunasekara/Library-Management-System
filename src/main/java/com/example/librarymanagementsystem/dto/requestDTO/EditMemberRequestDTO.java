package com.example.librarymanagementsystem.dto.requestDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditMemberRequestDTO  {
    private String username;
    private String userRole;

}
