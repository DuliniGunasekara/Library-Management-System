package com.example.librarymanagementsystem.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private String BASE_64_SECRET = "RjKP2Lae6Q/o4YN0BVh7/QdwUYp0LxfujuqE+tt46EB7yVR37ln9uLm/oanDpjVnXTvhdaLunPFMLzuM6JYaJV/ASAfA1RJxMfnEadsIvjr+PyVDIEP5qfw9et8Sw7WYpOoMcAOyTXHLcKh8jP8XkMJXnRNH1DpQRoYx/rbChRU+iU3FbpJQIryrf6gvseOIuofxgfy6lnTkK/8yIJy4axYvmI1NbcHeJdpS9A==";


    public TokenProvider() {
        BASE_64_SECRET = Base64.getEncoder().encodeToString(BASE_64_SECRET.getBytes());
    }

    public String createToken(final Authentication authentication) {

        final String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        final long currentTime = new Date().getTime();
        final Date tokenValidity = new Date(currentTime + 1000 * 2592000L);

        return Jwts.builder().setSubject(authentication.getName()).claim("auth", authorities).signWith(Keys.hmacShaKeyFor(BASE_64_SECRET.getBytes())).setExpiration(tokenValidity).compact();
    }

    public Authentication getAuthentication(final String token) {

        final Claims claims = Jwts.parserBuilder().setSigningKey(BASE_64_SECRET.getBytes()).build().parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(",")).filter(auth -> !auth.trim().isEmpty()).map(SimpleGrantedAuthority::new).toList();

        User user = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(user, token, authorities);

    }

    public boolean validateToken(final String token) {

        try {
            Jwts.parserBuilder().setSigningKey(BASE_64_SECRET.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            logger.error("Invalid Token");
        }
        return false;
    }
}
