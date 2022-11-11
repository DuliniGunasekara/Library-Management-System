package com.example.librarymanagementsystem.security;

import com.example.librarymanagementsystem.constants.UserRole;
import com.example.librarymanagementsystem.security.jwt.JwtConfigurer;
import com.example.librarymanagementsystem.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    protected SecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private JwtConfigurer jwtSecurityConfigurerAdapter() {
        return new JwtConfigurer(tokenProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeRequests(auth -> {
            auth.antMatchers("/api/member/**").hasAnyAuthority(UserRole.LIBRARIAN.toString());
            auth.antMatchers("/api/password").hasAnyAuthority(UserRole.LIBRARIAN.toString(),UserRole.MEMBER.toString());
            auth.antMatchers("/api/login").permitAll();
            auth.antMatchers("/api/book/**").hasAnyAuthority(UserRole.LIBRARIAN.toString());
            auth.antMatchers("/api/issue").hasAnyAuthority(UserRole.LIBRARIAN.toString());
            auth.antMatchers("/api/issue/history").hasAnyAuthority(UserRole.MEMBER.toString());
        }).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).httpBasic(Customizer.withDefaults()).apply(jwtSecurityConfigurerAdapter());
        return http.build();
    }
}
