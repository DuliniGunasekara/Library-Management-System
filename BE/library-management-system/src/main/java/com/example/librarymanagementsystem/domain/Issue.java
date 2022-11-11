package com.example.librarymanagementsystem.domain;

import com.example.librarymanagementsystem.constants.IssueStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "issue")
public class Issue {
    @Id
    @Indexed(unique = true)
    private String id;
    private List<Book> bookList;
    private String memberUsername;
    private LocalDate issuedDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;
    private IssueStatus issueStatus;
}
