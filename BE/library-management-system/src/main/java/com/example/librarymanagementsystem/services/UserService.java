package com.example.librarymanagementsystem.services;

import com.example.librarymanagementsystem.domain.AppUser;
import com.example.librarymanagementsystem.dto.requestDTO.mapper.UserRequestMapper;
import com.example.librarymanagementsystem.dto.responseDTO.mapper.UserResponseMapper;
import com.example.librarymanagementsystem.dto.requestDTO.RegisterRequestDTO;
import com.example.librarymanagementsystem.dto.responseDTO.RegisterResponseDTO;
import com.example.librarymanagementsystem.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserResponseMapper userResponseMapper;
    private final UserRequestMapper userRequestMapper;

    private final UserRepository userRepository;

    public UserService(UserResponseMapper userResponseMapper, UserRequestMapper userRequestMapper, UserRepository userRepository) {
        this.userResponseMapper = userResponseMapper;
        this.userRequestMapper = userRequestMapper;
        this.userRepository = userRepository;
    }

    public RegisterResponseDTO registerUserService(final RegisterRequestDTO registerRequestDTO){
        logger.info("In registerUserService method");

        AppUser existingUser = userRepository.findAppUserByUsername(registerRequestDTO.getUsername()).orElse(null);

        if(existingUser == null){
            AppUser newUser = userRepository.save(userRequestMapper
                    .mapRegisterRequestDTOtoAppUser(registerRequestDTO));

            return userResponseMapper.mapAppUserToRegisterResponseDTO(newUser);
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("In loadUserByUsername service method");

        AppUser existingUser = userRepository.findAppUserByUsername(username).orElse(null);
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

        if (existingUser == null) {
            String msg = "Username " + username + " not found!";
            logger.error(msg);
            throw new UsernameNotFoundException(msg);

        } else {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(existingUser.getUserRole().toString()));
        }
        return new User(existingUser.getUsername(), existingUser.getPassword(), simpleGrantedAuthorities);
    }
}
