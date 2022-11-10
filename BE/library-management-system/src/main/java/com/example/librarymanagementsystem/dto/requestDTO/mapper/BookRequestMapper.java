package com.example.librarymanagementsystem.dto.requestDTO.mapper;

import com.example.librarymanagementsystem.constants.BookStatus;
import com.example.librarymanagementsystem.constants.UserRole;
import com.example.librarymanagementsystem.domain.AppUser;
import com.example.librarymanagementsystem.domain.Book;
import com.example.librarymanagementsystem.dto.requestDTO.BookRequestDTO;
import com.example.librarymanagementsystem.dto.requestDTO.RegisterRequestDTO;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class BookRequestMapper {

    public Book mapBookRequestDTOtoBook(final BookRequestDTO bookRequestDTO){
        Book book = new Book();
        book.setName(bookRequestDTO.getName());
        book.setIsbnNumber(bookRequestDTO.getIsbnNumber());
        book.setBookStatus(BookStatus.valueOf(bookRequestDTO.getBookStatus()));
        book.setAuthor(bookRequestDTO.getAuthor());
        return book;
    }
}
