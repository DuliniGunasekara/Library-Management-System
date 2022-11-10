package com.example.librarymanagementsystem.repositories;

import com.example.librarymanagementsystem.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book,String> {

    Optional<Book> findBookById(final String id);

    Optional<Book> findBookByIsbnNumber(final String isbnNumber);

}
