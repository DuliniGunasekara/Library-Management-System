package com.example.librarymanagementsystem.dto.requestDTO.mapper;

import com.example.librarymanagementsystem.constants.IssueStatus;
import com.example.librarymanagementsystem.domain.Book;
import com.example.librarymanagementsystem.domain.Issue;
import com.example.librarymanagementsystem.dto.requestDTO.IssueRequestDTO;
import com.example.librarymanagementsystem.repositories.IssueRepository;
import com.example.librarymanagementsystem.util.UtilMethods;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Component
public class IssueRequestMapper {

    public Issue mapIssueRequestDTOtoIssue(final IssueRequestDTO issueRequestDTO, final List<Book> bookList){
        Issue issue = new Issue();
        issue.setBookList(bookList);
        issue.setIssuedDate(LocalDate.now());
        issue.setDueDate(UtilMethods.convertStringToDateFormat(issueRequestDTO.getDueDate()));
        issue.setMemberUsername(issueRequestDTO.getMemberUsername());
        issue.setReturnedDate(LocalDate.now());
        issue.setIssueStatus(IssueStatus.ISSUED);
        return issue;
    }
}
