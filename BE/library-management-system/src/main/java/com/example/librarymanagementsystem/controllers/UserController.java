package com.example.librarymanagementsystem.controllers;

import com.example.librarymanagementsystem.constants.SecurityConstants;
import com.example.librarymanagementsystem.domain.AppUser;
import com.example.librarymanagementsystem.dto.requestDTO.*;
import com.example.librarymanagementsystem.dto.responseDTO.EditUserResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.LoginResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.RegisterResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.UserResponseDTO;
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
import org.springframework.util.StringUtils;
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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody final UserRequestDTO userRequestDTO){
        logger.info("In login controller");

        if(!ValidateRequest.validateUserRequestDTO(userRequestDTO)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userRequestDTO.getUsername(), userRequestDTO.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwtToken = tokenProvider.createToken(authentication);
        LoginResponseDTO loginResponse = userResponseMapper.mapToLoginResponseDTO(jwtToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(SecurityConstants.AUTHORIZATION_HEADER, SecurityConstants.TOKEN_PREFIX + jwtToken);
        return new ResponseEntity<>( loginResponse,httpHeaders,HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody final RegisterRequestDTO registerRequestDTO){
        logger.info("In register controller");

        if(!ValidateRequest.validateRegisterRequestDTO(registerRequestDTO)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

       UserResponseDTO userResponseDTO = userService.registerUserService(registerRequestDTO);

        if(userResponseDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userResponseDTO,HttpStatus.CREATED);
    }

    @PatchMapping("/password")
    public HttpStatus changePassword(@RequestBody final ChangePasswordRequestDTO changePasswordRequestDTO){
        logger.info("In changePassword controller");

        if(!ValidateRequest.validateChangePasswordRequestDTO(changePasswordRequestDTO)){
            return HttpStatus.BAD_REQUEST;
        }

        AppUser appUser = userService.changePasswordService(changePasswordRequestDTO);

        if(appUser == null){
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.CREATED;
    }

    @PutMapping("/edit/{username}")
    public ResponseEntity<EditUserResponseDTO> editUser(@PathVariable("username") final String username, @RequestBody final EditUserRequestDTO editUserRequestDTO){
        if(!StringUtils.hasLength(username) && !ValidateRequest.validateEditUserRequestDTO(editUserRequestDTO)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        EditUserResponseDTO editUserResponseDTO = userService.editUserService(username,editUserRequestDTO);

        if(editUserResponseDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(editUserResponseDTO,HttpStatus.CREATED);

    }
}
