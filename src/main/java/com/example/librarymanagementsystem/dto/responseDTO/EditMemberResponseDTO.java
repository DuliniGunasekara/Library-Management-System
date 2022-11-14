package com.example.librarymanagementsystem.dto.responseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditMemberResponseDTO extends MemberResponseDTO {
    private String userRole;
}
