package com.example.librarymanagementsystem.controllers;

import com.example.librarymanagementsystem.constants.SecurityConstants;
import com.example.librarymanagementsystem.dto.requestDTO.LoginRequestDTO;
import com.example.librarymanagementsystem.dto.responseDTO.LoginResponseDTO;
import com.example.librarymanagementsystem.dto.requestDTO.RegisterRequestDTO;
import com.example.librarymanagementsystem.dto.responseDTO.RegisterResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.mapper.UserResponseMapper;
import com.example.librarymanagementsystem.security.jwt.TokenProvider;
import com.example.librarymanagementsystem.services.UserService;
import com.example.librarymanagementsystem.util.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;

    private final UserService userService;

   private final UserResponseMapper userResponseMapper;



    public UserController(AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider, UserService userService, UserResponseMapper userResponseMapper) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
        this.userResponseMapper = userResponseMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody final LoginRequestDTO loginRequestDTO){
        logger.info("In login controller");

        if(!ValidateRequest.validateLoginRequestDTO(loginRequestDTO)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwtToken = tokenProvider.createToken(authentication);
        LoginResponseDTO loginResponse = userResponseMapper.mapToLoginResponseDTO(jwtToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(SecurityConstants.AUTHORIZATION_HEADER, SecurityConstants.TOKEN_PREFIX + jwtToken);
        return new ResponseEntity<>( loginResponse,httpHeaders,HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody final RegisterRequestDTO registerRequestDTO){
        logger.info("In register controller");

        if(!ValidateRequest.validateRegisterRequestDTO(registerRequestDTO)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        RegisterResponseDTO registerResponseDTO = userService.registerUserService(registerRequestDTO);

        if(registerResponseDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(registerResponseDTO,HttpStatus.CREATED);
    }

}
