package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.constants.UserRole;
import com.example.librarymanagementsystem.domain.Member;
import com.example.librarymanagementsystem.dto.requestDTO.RegisterRequestDTO;
import com.example.librarymanagementsystem.dto.requestDTO.mapper.MemberRequestMapper;
import com.example.librarymanagementsystem.dto.responseDTO.MemberResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.mapper.UserResponseMapper;
import com.example.librarymanagementsystem.repositories.MemberRepository;
import com.example.librarymanagementsystem.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class UserServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private UserService userService;

    @Spy
    private UserResponseMapper userResponseMapper;

    @Spy
    private MemberRequestMapper memberRequestMapper;

    @Spy
    private PasswordEncoder passwordEncoder;


    @Test
    public void getAllMembersServiceTest(){

        Member member1 = new Member();
        member1.setId("636e13cea29f183870443aef");
        member1.setUsername("dulini");
        member1.setEligible(true);
        member1.setUserRole(UserRole.MEMBER);

        Member member2 = new Member();
        member2.setId("636e13cea29f183870443erf");
        member2.setUsername("nimasha");
        member2.setEligible(true);
        member2.setUserRole(UserRole.MEMBER);

        List<Member> memberList = new ArrayList<>();
        memberList.add(member1);
        memberList.add(member2);

        when(memberRepository.findMemberByUserRole(UserRole.MEMBER.toString())).thenReturn(memberList);

        List<MemberResponseDTO> existingMembers = userService.getAllMembersService();
        assertEquals(2,existingMembers.size());
    }

    @Test
    public void registerUserServiceTest(){
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO();
        registerRequestDTO.setUsername("Duli96");
        registerRequestDTO.setPassword("dulini123");
        registerRequestDTO.setUserRole("MEMBER");

        Member member = new Member();
        member.setUsername("Duli96");
        member.setPassword("dulini123");
        member.setUserRole(UserRole.MEMBER);

        when(memberRepository.save(any(Member.class))).thenReturn(member);

        MemberResponseDTO newUser = userService.registerUserService(registerRequestDTO);

        assertEquals(registerRequestDTO.getUsername(),newUser.getUsername());
    }
}
