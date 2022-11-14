package com.example.librarymanagementsystem.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookRequestDTO {
    private String isbnNumber;
    private String name;
    private String author;
    private String bookStatus;
}
