package com.example.librarymanagementsystem.repositories;

import com.example.librarymanagementsystem.domain.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;

public interface UserRepository extends MongoRepository<AppUser,String> {

    Optional<AppUser> findAppUserByUsername(final String username);
}
