package com.example.librarymanagementsystem.dto.responseDTO.mapper;

import com.example.librarymanagementsystem.domain.Book;
import com.example.librarymanagementsystem.dto.responseDTO.BookDeleteResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.BookResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class BookResponseMapper {

    public BookResponseDTO mapBookToBookResponseDTO(final Book book){
        BookResponseDTO bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setId(book.getId());
        bookResponseDTO.setName(book.getName());
        bookResponseDTO.setIsbnNumber(book.getIsbnNumber());
        bookResponseDTO.setAuthor(book.getAuthor());
        bookResponseDTO.setBookStatus(book.getBookStatus().toString());
        return bookResponseDTO;
    }

    public BookDeleteResponseDTO mapBookToBookDeleteResponseDTO(final Book book){
        BookDeleteResponseDTO bookDeleteResponseDTO = new BookDeleteResponseDTO();
        bookDeleteResponseDTO.setId(book.getId());
        bookDeleteResponseDTO.setName(book.getName());
        bookDeleteResponseDTO.setIsbnNumber(book.getIsbnNumber());
        bookDeleteResponseDTO.setAuthor(book.getAuthor());
        bookDeleteResponseDTO.setBookStatus(book.getBookStatus().toString());
        return bookDeleteResponseDTO;
    }
}
