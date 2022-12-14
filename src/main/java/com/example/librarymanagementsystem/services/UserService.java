package com.example.librarymanagementsystem.services;

import com.example.librarymanagementsystem.constants.UserRole;
import com.example.librarymanagementsystem.domain.AppUser;
import com.example.librarymanagementsystem.domain.Member;
import com.example.librarymanagementsystem.dto.requestDTO.ChangePasswordRequestDTO;
import com.example.librarymanagementsystem.dto.requestDTO.EditMemberRequestDTO;
import com.example.librarymanagementsystem.dto.requestDTO.RegisterRequestDTO;
import com.example.librarymanagementsystem.dto.requestDTO.mapper.MemberRequestMapper;
import com.example.librarymanagementsystem.dto.responseDTO.EditMemberResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.MemberDeleteResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.MemberResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.mapper.UserResponseMapper;
import com.example.librarymanagementsystem.repositories.MemberRepository;
import com.example.librarymanagementsystem.util.ErrorMessageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserResponseMapper userResponseMapper;
    private final MemberRequestMapper memberRequestMapper;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;


    public UserService(UserResponseMapper userResponseMapper, MemberRequestMapper memberRequestMapper, MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.userResponseMapper = userResponseMapper;
        this.memberRequestMapper = memberRequestMapper;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<MemberResponseDTO> getAllMembersService() {
        logger.info("In getAllMembersService method");

        List<Member> memberList = memberRepository.findMemberByUserRole(UserRole.MEMBER.toString());

        if (memberList.isEmpty()) {
            return Collections.emptyList();
        }

        return memberList.stream().map(userResponseMapper::mapMemberToUserResponseDTO).toList();
    }

    public MemberResponseDTO getMemberByUsernameService(final String username) {
        logger.info("In getMemberByUsernameService method");

        Member existingUser = getExistingUser(username);
        if (existingUser == null) {
            logger.error(ErrorMessageGenerator.userNameNotFound(username));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessageGenerator.userNameNotFound(username));
        }
        return userResponseMapper.mapMemberToUserResponseDTO(existingUser);
    }

    public MemberResponseDTO registerUserService(final RegisterRequestDTO registerRequestDTO) {
        logger.info("In registerUserService method");

        Member existingUser = getExistingUser(registerRequestDTO.getUsername());
        if (existingUser == null) {
            registerRequestDTO.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
            Member newUser = memberRepository.save(memberRequestMapper.mapRegisterRequestDTOtoAppUser(registerRequestDTO));
            return userResponseMapper.mapMemberToUserResponseDTO(newUser);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, ErrorMessageGenerator.memberUsernameAlreadyExisting(registerRequestDTO.getUsername()));
    }

    public EditMemberResponseDTO editUserService(final String username, final EditMemberRequestDTO editMemberRequestDTO) {
        logger.info("In editUserService method");

        Member existingUser = getExistingUser(username);
        if (!username.equals(editMemberRequestDTO.getUsername()) && getExistingUser(editMemberRequestDTO.getUsername()) != null) {
            logger.error(ErrorMessageGenerator.usernameNotAvailable());
            throw new ResponseStatusException(HttpStatus.CONFLICT, ErrorMessageGenerator.memberUsernameAlreadyExisting(editMemberRequestDTO.getUsername()));

        }

        if (existingUser == null) {
            logger.error(ErrorMessageGenerator.userNameNotFound(username));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessageGenerator.userNameNotFound(username));
        }
        existingUser.setUsername(editMemberRequestDTO.getUsername());
        existingUser.setUserRole(UserRole.valueOf(editMemberRequestDTO.getUserRole()));
        Member updatedUser = memberRepository.save(existingUser);
        return userResponseMapper.mapMemberToEditUserResponseDTO(updatedUser);
    }


    public AppUser changePasswordService(final ChangePasswordRequestDTO changePasswordRequestDTO) {
        logger.info("In changePasswordService method");

        Member appUser = getExistingUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if (appUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,ErrorMessageGenerator.userNotFound(SecurityContextHolder.getContext().getAuthentication().getName()));
        }else if(!passwordEncoder.matches(changePasswordRequestDTO.getOldPassword(), appUser.getPassword())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,ErrorMessageGenerator.oldPasswordNotMatch());
        } else if(changePasswordRequestDTO.getNewPassword().equals(changePasswordRequestDTO.getOldPassword())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,ErrorMessageGenerator.newPasswordHasUsedBefore());
        }
        appUser.setPassword(passwordEncoder.encode(changePasswordRequestDTO.getNewPassword()));
        return memberRepository.save(appUser);
    }

    public MemberDeleteResponseDTO deleteUserService(final String id) {
        logger.info("In deleteUserService method");

        Member existingUser = memberRepository.findMemberById(id).orElse(null);
        if (existingUser == null) {
            logger.error(ErrorMessageGenerator.userNotFound(id));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessageGenerator.userNotFound(id));

        }
        memberRepository.delete(existingUser);
        return userResponseMapper.mapMemberToMemberDeleteResponseDTO(existingUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        logger.info("In loadUserByUsername service method");

        AppUser existingUser = getExistingUser(username);
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        if (existingUser == null) {
            logger.error(ErrorMessageGenerator.userNameNotFound(username));

        } else {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(existingUser.getUserRole().toString()));
        }
        return new User(existingUser.getUsername(), existingUser.getPassword(), simpleGrantedAuthorities);
    }

    public Member getExistingUser(final String username) {
        return memberRepository.findMemberByUsername(username).orElse(null);
    }
}
