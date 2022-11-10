package com.example.librarymanagementsystem.dto.responseDTO;

import com.example.librarymanagementsystem.constants.BookStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponseDTO {
    private String id;
    private String isbnNumber;
    private String name;
    private String author;
    private String bookStatus;
}
