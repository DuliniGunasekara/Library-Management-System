package com.example.librarymanagementsystem.repositories;

import com.example.librarymanagementsystem.domain.Issue;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends MongoRepository<Issue, String> {
    List<Issue> findAllByMemberUsername(final String memberUsername);
}
