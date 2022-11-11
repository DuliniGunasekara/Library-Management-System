package com.example.librarymanagementsystem.repositories;

import com.example.librarymanagementsystem.domain.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends MongoRepository<Member,String> {

    Optional<Member> findMemberByUsername(final String username);
    Optional<Member> findMemberById(final String id);

    List<Member> findMemberByUserRole(final String userRole);
}
