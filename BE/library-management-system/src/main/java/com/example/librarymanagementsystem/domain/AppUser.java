package com.example.librarymanagementsystem.domain;

import com.example.librarymanagementsystem.constants.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "user")
public class AppUser {

    @Id
    protected String id;
    @Indexed(unique = true)
    protected String username;
    protected String password;
    protected UserRole userRole;

    public AppUser(UserRole userRole) {
        this.userRole = userRole;
    }
}