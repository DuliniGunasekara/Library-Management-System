package com.example.librarymanagementsystem.domain;

import com.example.librarymanagementsystem.constants.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member extends AppUser {

    private static final UserRole userRole = UserRole.MEMBER;
    private boolean isEligible;

    public Member() {
        super(userRole);
        this.isEligible = true;
    }
}
