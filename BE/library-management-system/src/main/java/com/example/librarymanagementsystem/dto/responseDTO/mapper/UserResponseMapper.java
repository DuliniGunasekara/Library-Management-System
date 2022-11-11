package com.example.librarymanagementsystem.dto.responseDTO.mapper;

import com.example.librarymanagementsystem.domain.AppUser;
import com.example.librarymanagementsystem.domain.Member;
import com.example.librarymanagementsystem.dto.responseDTO.EditMemberResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.LoginResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.MemberDeleteResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.MemberResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {

    public MemberResponseDTO mapMemberToUserResponseDTO(final Member member){
        MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
        memberResponseDTO.setId(member.getId());
        memberResponseDTO.setUsername(member.getUsername());
        return memberResponseDTO;
    }

    public MemberDeleteResponseDTO mapMemberToMemberDeleteResponseDTO(final Member member){
        MemberDeleteResponseDTO memberDeleteResponseDTO = new MemberDeleteResponseDTO();
        memberDeleteResponseDTO.setId(member.getId());
        memberDeleteResponseDTO.setUsername(member.getUsername());
        return memberDeleteResponseDTO;
    }
    public LoginResponseDTO mapToLoginResponseDTO(final String jwt){
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setJwtToken(jwt);
        return loginResponseDTO;
    }

    public EditMemberResponseDTO mapMemberToEditUserResponseDTO(final Member member){
        EditMemberResponseDTO editUserResponseDTO = new EditMemberResponseDTO();
        editUserResponseDTO.setId(member.getId());
        editUserResponseDTO.setUsername(member.getUsername());
        editUserResponseDTO.setUserRole(member.getUserRole().toString());
        return editUserResponseDTO;
    }
}
