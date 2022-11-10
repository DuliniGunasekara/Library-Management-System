package com.example.librarymanagementsystem.services;

import com.example.librarymanagementsystem.constants.ErrorMessage;
import com.example.librarymanagementsystem.constants.UserRole;
import com.example.librarymanagementsystem.domain.AppUser;
import com.example.librarymanagementsystem.dto.requestDTO.ChangePasswordRequestDTO;
import com.example.librarymanagementsystem.dto.requestDTO.EditUserRequestDTO;
import com.example.librarymanagementsystem.dto.requestDTO.RegisterRequestDTO;
import com.example.librarymanagementsystem.dto.requestDTO.mapper.UserRequestMapper;
import com.example.librarymanagementsystem.dto.responseDTO.EditUserResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.RegisterResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.UserResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.mapper.UserResponseMapper;
import com.example.librarymanagementsystem.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserResponseMapper userResponseMapper;
    private final UserRequestMapper userRequestMapper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;



    public UserService(UserResponseMapper userResponseMapper, UserRequestMapper userRequestMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userResponseMapper = userResponseMapper;
        this.userRequestMapper = userRequestMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO registerUserService(final RegisterRequestDTO registerRequestDTO){
        logger.info("In registerUserService method");

        AppUser existingUser = getExistingUser(registerRequestDTO.getUsername());

        if(existingUser == null){
            AppUser newUser = userRepository.save(userRequestMapper
                    .mapRegisterRequestDTOtoAppUser(registerRequestDTO));
            return userResponseMapper.mapAppUserToUserResponseDTO(newUser);
        }
        return null;
    }

    public EditUserResponseDTO editUserService(final String username, final EditUserRequestDTO editUserRequestDTO){
        logger.info("In editUserService method");

        AppUser existingUser = getExistingUser(username);

        if(existingUser == null){
            logger.error(ErrorMessage.USERNAME_NOT_FOUND);
            throw new UsernameNotFoundException(ErrorMessage.USERNAME_NOT_FOUND);
        }
        existingUser.setUsername(editUserRequestDTO.getUsername());
        existingUser.setUserRole(UserRole.valueOf(editUserRequestDTO.getUserRole()));
        AppUser updatedUser = userRepository.save(existingUser);
        return userResponseMapper.mapAppUserToEditUserResponseDTO(updatedUser);
    }


    public AppUser changePasswordService(final ChangePasswordRequestDTO changePasswordRequestDTO){
        logger.info("In changePasswordService method");
        AppUser appUser = getExistingUser(SecurityContextHolder.getContext().getAuthentication().getName());

        if(appUser == null || !passwordEncoder.matches(changePasswordRequestDTO.getOldPassword(),appUser.getPassword())){
            return null;
        }

        appUser.setPassword(passwordEncoder.encode(changePasswordRequestDTO.getNewPassword()));
        return userRepository.save(appUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        logger.info("In loadUserByUsername service method");

        AppUser existingUser = getExistingUser(username);
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

        if (existingUser == null) {
            logger.error(ErrorMessage.USERNAME_NOT_FOUND);
            throw new UsernameNotFoundException(ErrorMessage.USERNAME_NOT_FOUND);

        } else {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(existingUser.getUserRole().toString()));
        }
        return new User(existingUser.getUsername(), existingUser.getPassword(), simpleGrantedAuthorities);
    }

    public AppUser getExistingUser(final String username){
        return userRepository.findAppUserByUsername(username).orElse(null);
    }

}
