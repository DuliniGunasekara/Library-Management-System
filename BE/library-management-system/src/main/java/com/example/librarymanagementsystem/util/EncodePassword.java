package com.example.librarymanagementsystem.util;

import org.springframework.security.crypto.password.PasswordEncoder;

public class EncodePassword {

    PasswordEncoder passwordEncoder;

    public  String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

}
