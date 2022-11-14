package com.example.librarymanagementsystem.dto.responseDTO;

import com.example.librarymanagementsystem.domain.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class IssueResponseDTO {
    private String id;
    private List<Book> bookList;
    private String memberUsername;
    private String issuedDate;
    private String dueDate;
    private String issueStatus;
}