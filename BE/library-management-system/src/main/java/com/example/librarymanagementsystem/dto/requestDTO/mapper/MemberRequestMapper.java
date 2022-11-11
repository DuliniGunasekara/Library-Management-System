package com.example.librarymanagementsystem.dto.requestDTO.mapper;

import com.example.librarymanagementsystem.constants.UserRole;
import com.example.librarymanagementsystem.domain.AppUser;
import com.example.librarymanagementsystem.domain.Member;
import com.example.librarymanagementsystem.dto.requestDTO.RegisterRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MemberRequestMapper {

    PasswordEncoder passwordEncoder;
    public Member mapRegisterRequestDTOtoAppUser(final RegisterRequestDTO registerRequestDTO){
        Member appUser = new Member();
        appUser.setUsername(registerRequestDTO.getUsername());
        appUser.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        return appUser;
    }
}
