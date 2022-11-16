package com.example.librarymanagementsystem.controllers;

import com.example.librarymanagementsystem.dto.requestDTO.BookRequestDTO;
import com.example.librarymanagementsystem.dto.responseDTO.BookDeleteResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.BookResponseDTO;
import com.example.librarymanagementsystem.services.BookService;
import com.example.librarymanagementsystem.util.ErrorMessageGenerator;
import com.example.librarymanagementsystem.util.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBookList(){
        logger.info("In getAllBookList controller");

        List<BookResponseDTO> bookResponseDTOList = bookService.getAllBooksService();

        if(bookResponseDTOList == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(bookResponseDTOList,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable("id") final String id){
        logger.info("In getBookById controller");

        BookResponseDTO bookResponseDTO = bookService.getBookByIdService(id);
        return new ResponseEntity<>(bookResponseDTO,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<BookResponseDTO> addBook(@RequestBody final BookRequestDTO bookRequestDTO){
        logger.info("In addBook controller");

        if(!ValidateRequest.validateBookRequestDTO(bookRequestDTO)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageGenerator.invalidRequestBody());
        }

        BookResponseDTO bookResponseDTO = bookService.addBookService(bookRequestDTO);

        if(bookResponseDTO == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessageGenerator.failedToAddResource());
        }
        return new ResponseEntity<>(bookResponseDTO,HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<BookResponseDTO> editBook(@PathVariable("id") final String id, @RequestBody final BookRequestDTO bookRequestDTO){
        logger.info("In editBook controller");

        if(!StringUtils.hasLength(id) && ValidateRequest.validateBookRequestDTO(bookRequestDTO)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageGenerator.invalidRequestBody());
        }

        BookResponseDTO bookResponseDTO = bookService.editBookService(id,bookRequestDTO);

        if(bookResponseDTO == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessageGenerator.failedToEditResource());
        }
        return new ResponseEntity<>(bookResponseDTO,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BookDeleteResponseDTO> deleteBook(@PathVariable("id") final String id){
        logger.info("In deleteBook controller");

        BookDeleteResponseDTO bookDeleteResponseDTO = bookService.deleteBookService(id);

        if(bookDeleteResponseDTO == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessageGenerator.internalServerError());
        }
        return new ResponseEntity<>(bookDeleteResponseDTO,HttpStatus.NO_CONTENT);
    }
}
