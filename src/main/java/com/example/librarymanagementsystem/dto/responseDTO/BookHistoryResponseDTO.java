package com.example.librarymanagementsystem.dto.responseDTO;

import com.example.librarymanagementsystem.domain.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookHistoryResponseDTO {

    private String issueId;
    private List<Book> bookList;
    private String issuedDate;
    private String dueDate;
    private String returnedDate;
    private String status;
}
