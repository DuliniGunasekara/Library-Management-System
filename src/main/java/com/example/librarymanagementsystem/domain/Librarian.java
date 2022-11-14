package com.example.librarymanagementsystem.domain;

import com.example.librarymanagementsystem.constants.UserRole;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Librarian extends AppUser {

    private static final UserRole userRole = UserRole.LIBRARIAN;

    public Librarian() {
        super(userRole);
    }
}
