package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.constants.BookStatus;
import com.example.librarymanagementsystem.domain.Book;
import com.example.librarymanagementsystem.dto.requestDTO.BookRequestDTO;
import com.example.librarymanagementsystem.dto.requestDTO.mapper.BookRequestMapper;
import com.example.librarymanagementsystem.dto.responseDTO.BookResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.mapper.BookResponseMapper;
import com.example.librarymanagementsystem.repositories.BookRepository;
import com.example.librarymanagementsystem.services.BookService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Spy
    private BookResponseMapper bookResponseMapper;
    @Spy
    private BookRequestMapper bookRequestMapper;
    @InjectMocks
    private BookService bookService;


    @Test void getBookByIdServiceTest(){

        Book book = new Book()
                .setId("1")
                .setBookStatus(BookStatus.AVAILABLE)
                .setName("Wuthering Heights")
                .setAuthor("Emily Bronte")
                .setIsbnNumber("ISBN1234");

        when(bookRepository.findBookById("1")).thenReturn(Optional.of(book));

        BookResponseDTO getBook = bookService.getBookByIdService("1");
        assertEquals("Wuthering Heights",getBook.getName());
        assertEquals("Emily Bronte",getBook.getAuthor());
        assertEquals("ISBN1234",getBook.getIsbnNumber());

    }

    @Test public void getAllBooksServiceTest(){
        Book book1 = new Book()
                .setId("636e157ca29f183870443af1")
                .setName("Pride and Premeditation")
                .setBookStatus(BookStatus.AVAILABLE)
                .setAuthor("Tirzah Price")
                .setIsbnNumber("ISBN1234");

        Book book2 = new Book()
                .setId("636e1eec65c50c2dda6422a8")
                .setBookStatus(BookStatus.AVAILABLE)
                .setName("Wuthering Heights")
                .setAuthor("Emily Bronte")
                .setIsbnNumber("ISBN1234");

        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);

        when(bookRepository.findAll()).thenReturn(bookList);

        List<BookResponseDTO> returnedBooks = bookService.getAllBooksService();
        assertEquals(2,returnedBooks.size());
        assertEquals("Pride and Premeditation",returnedBooks.get(0).getName());
    }

    @Test public void getAllBooksServiceTestFailed(){
        Book book1 = new Book()
                .setId("636e157ca29f183870443af1")
                .setName("Pride and Premeditation")
                .setBookStatus(BookStatus.AVAILABLE)
                .setAuthor("Tirzah Price")
                .setIsbnNumber("ISBN1234");

        Book book2 = new Book()
                .setId("636e1eec65c50c2dda6422a8")
                .setBookStatus(BookStatus.AVAILABLE)
                .setName("Wuthering Heights")
                .setAuthor("Emily Bronte")
                .setIsbnNumber("ISBN1234");

        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);

        when(bookRepository.findAll()).thenReturn(bookList);

        List<BookResponseDTO> returnedBooks = bookService.getAllBooksService();
        assertEquals(1,returnedBooks.size());
        assertEquals("Pride and Premeditation",returnedBooks.get(0).getName());
    }

    @Test public void addBookServiceTest(){

        BookRequestDTO bookRequestDTO = new BookRequestDTO();
        bookRequestDTO.setName("Thirteen Doorways, Wolves Behind Them");
        bookRequestDTO.setAuthor("Laura Ruby");
        bookRequestDTO.setBookStatus("AVAILABLE");
        bookRequestDTO.setIsbnNumber("ISBN12");

        Book book = new Book()
                .setId("636e1eec65c50c2dda6422a8")
                .setName("Thirteen Doorways, Wolves Behind Them")
                .setAuthor("Laura Ruby")
                .setBookStatus(BookStatus.AVAILABLE)
                .setIsbnNumber("ISBN12");

        when((bookRepository.save(any(Book.class)))).thenReturn(book);

        BookResponseDTO bookResponseDTO = bookService.addBookService(bookRequestDTO);
        assertEquals(bookResponseDTO.getName(),bookRequestDTO.getName());
        assertEquals(bookResponseDTO.getAuthor(),bookRequestDTO.getAuthor());
        assertEquals(bookResponseDTO.getIsbnNumber(),bookRequestDTO.getIsbnNumber());
        assertEquals(bookResponseDTO.getBookStatus(),bookRequestDTO.getBookStatus());
    }
}


