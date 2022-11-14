package com.example.librarymanagementsystem.dto.requestDTO;

import com.example.librarymanagementsystem.domain.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class IssueRequestDTO {
    private List<String> bookList;
    private String memberUsername;
    private String dueDate;
}
