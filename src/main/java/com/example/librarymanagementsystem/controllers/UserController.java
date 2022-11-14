package com.example.librarymanagementsystem.controllers;

import com.example.librarymanagementsystem.constants.SecurityConstants;
import com.example.librarymanagementsystem.domain.AppUser;
import com.example.librarymanagementsystem.dto.requestDTO.*;
import com.example.librarymanagementsystem.dto.responseDTO.*;
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

import java.util.List;

@RestController
@RequestMapping("/api")
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

    @GetMapping("/member")
    public  ResponseEntity<List<MemberResponseDTO>> getAllMembers(){
        logger.info("In getAllMembers controller");

        List<MemberResponseDTO> memberResponseDTOList = userService.getAllMembersService();

        return new ResponseEntity<>(memberResponseDTOList,HttpStatus.OK);
    }

    @GetMapping("/member/{username}")
    public ResponseEntity<MemberResponseDTO> getMemberByUsername(@PathVariable("username") final String username){
        logger.info("In getMemberByUsername controller");

        MemberResponseDTO memberResponseDTO = userService.getMemberByUsernameService(username);

        if(memberResponseDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(memberResponseDTO,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody final MemberRequestDTO userRequestDTO){
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

    @PostMapping("/member/register")
    public ResponseEntity<MemberResponseDTO> register(@RequestBody final RegisterRequestDTO registerRequestDTO){
        logger.info("In register controller");

        if(!ValidateRequest.validateRegisterRequestDTO(registerRequestDTO)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

       MemberResponseDTO memberResponseDTO = userService.registerUserService(registerRequestDTO);

        if(memberResponseDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(memberResponseDTO,HttpStatus.CREATED);
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

    @PutMapping("/member/edit/{username}")
    public ResponseEntity<EditMemberResponseDTO> editUser(@PathVariable("username") final String username, @RequestBody final EditMemberRequestDTO editMemberRequestDTO){
        logger.info("In editUser controller");

        if(!StringUtils.hasLength(username) && !ValidateRequest.validateEditUserRequestDTO(editMemberRequestDTO)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        EditMemberResponseDTO editUserResponseDTO = userService.editUserService(username, editMemberRequestDTO);

        if(editUserResponseDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(editUserResponseDTO,HttpStatus.CREATED);

    }

    @DeleteMapping ("/member/delete/{id}")
    public ResponseEntity<MemberDeleteResponseDTO> deleteUser(@PathVariable("id") final String id){
        logger.info("In deleteUser controller");

        if(!StringUtils.hasLength(id)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        MemberDeleteResponseDTO memberResponseDTO = userService.deleteUserService(id);

        if(memberResponseDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(memberResponseDTO,HttpStatus.OK);

    }
}
