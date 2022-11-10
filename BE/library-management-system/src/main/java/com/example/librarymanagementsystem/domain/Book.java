package com.example.librarymanagementsystem.domain;

import com.example.librarymanagementsystem.constants.BookStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "book")
public class Book {

    @Id
    @Indexed(unique = true)
    private String id;
    private String isbnNumber;
    private String name;
    private String author;
    private BookStatus bookStatus;
}
