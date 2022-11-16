package com.example.librarymanagementsystem.domain;

import com.example.librarymanagementsystem.constants.BookStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Document(collection = "book")
public class Book {

    @Id
    @Indexed(unique = true)
    private String id;
    private String isbnNumber;
    private String name;
    private String author;

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", isbnNumber='" + isbnNumber + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", bookStatus=" + bookStatus +
                '}';
    }

    private BookStatus bookStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(isbnNumber, book.isbnNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbnNumber);
    }
}
