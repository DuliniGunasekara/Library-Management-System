package com.example.librarymanagementsystem.services;

import com.example.librarymanagementsystem.constants.BookStatus;
import com.example.librarymanagementsystem.domain.Book;
import com.example.librarymanagementsystem.domain.Issue;
import com.example.librarymanagementsystem.dto.requestDTO.BookRequestDTO;
import com.example.librarymanagementsystem.dto.requestDTO.mapper.BookRequestMapper;
import com.example.librarymanagementsystem.dto.responseDTO.BookDeleteResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.BookResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.mapper.BookResponseMapper;
import com.example.librarymanagementsystem.repositories.BookRepository;
import com.example.librarymanagementsystem.repositories.IssueRepository;
import com.example.librarymanagementsystem.util.ErrorMessageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    private final BookResponseMapper bookResponseMapper;
    private final BookRequestMapper bookRequestMapper;

    private final IssueRepository issueRepository;

    public BookService(BookRepository bookRepository, BookResponseMapper bookResponseMapper, BookRequestMapper bookRequestMapper, IssueRepository issueRepository) {
        this.bookRepository = bookRepository;
        this.bookResponseMapper = bookResponseMapper;
        this.bookRequestMapper = bookRequestMapper;

        this.issueRepository = issueRepository;
    }

    public List<BookResponseDTO> getAllBooksService() {
        logger.info("In getAllBooksService method");

        List<Book> bookList = bookRepository.findAll();
        if (bookList.isEmpty()) {
            return Collections.emptyList();
        }
        return bookList.stream().map(bookResponseMapper::mapBookToBookResponseDTO).toList();
    }

    public BookResponseDTO getBookByIdService(final String id) {
        logger.info("In getBookByIdService method");

        Book book = getBookById(id);

        if (book == null) {
            logger.error(ErrorMessageGenerator.bookNotFound(id));
            return null;
        }
        return bookResponseMapper.mapBookToBookResponseDTO(book);
    }

    public BookResponseDTO addBookService(final BookRequestDTO bookRequestDTO) {
        logger.info("In getBookByIdService method");

        Book book = bookRepository.findBookByIsbnNumber(bookRequestDTO.getIsbnNumber()).orElse(null);

        if (book != null) {
            logger.error(ErrorMessageGenerator.bookAlreadyExisting(bookRequestDTO.getIsbnNumber()));
            return null;
        }

        bookRequestDTO.setBookStatus(BookStatus.AVAILABLE.toString());
        Book newBook = bookRepository.save(bookRequestMapper.mapBookRequestDTOtoBook(bookRequestDTO));
        return bookResponseMapper.mapBookToBookResponseDTO(newBook);
    }

    public BookResponseDTO editBookService(final String id, final BookRequestDTO bookRequestDTO) {
        logger.info("In editBookService method");

        Book book = getBookById(id);

        if (book == null) {
            logger.error(ErrorMessageGenerator.bookNotFound(id));
            return null;
        }
        if (!book.getIsbnNumber().equals(bookRequestDTO.getIsbnNumber())) {
            Book checkISBNBook = bookRepository.findBookByIsbnNumber(bookRequestDTO.getIsbnNumber()).orElse(null);
            if (checkISBNBook != null) {
                logger.error(ErrorMessageGenerator.bookISBNisExisting());
                return null;
            }
        }
        book.setName(bookRequestDTO.getName());
        book.setIsbnNumber(bookRequestDTO.getIsbnNumber());
        book.setAuthor(bookRequestDTO.getAuthor());

        Book editedBook = bookRepository.save(book);
        return bookResponseMapper.mapBookToBookResponseDTO(editedBook);
    }

    public BookDeleteResponseDTO deleteBookService(final String id) {
        logger.info("In deleteBookService method");

        Book book = getBookById(id);

        if (book == null) {
            logger.error(ErrorMessageGenerator.bookNotFound(id));
            return null;
        }

        List<Issue> issueList = issueRepository.findAll();
       issueList =  issueList.stream().map(issue -> {
          Book book1 = issue.getBookList().stream().filter(b->b.getId().equals(id)).findAny().orElse(new Book());
          issue.getBookList().remove(book1);
          return issue;

       }).toList();
       issueRepository.saveAll(issueList);

        bookRepository.delete(book);
        return bookResponseMapper.mapBookToBookDeleteResponseDTO(book);
    }

    public void updateBookAvailability(final Book book) {

        if (book.getBookStatus().equals(BookStatus.AVAILABLE)) {
            book.setBookStatus(BookStatus.NOT_AVAILABLE);
        } else {
            book.setBookStatus(BookStatus.AVAILABLE);
        }
        bookRepository.save(book);
    }

    public Book getBookById(final String id) {
        return bookRepository.findBookById(id).orElse(null);
    }
}


