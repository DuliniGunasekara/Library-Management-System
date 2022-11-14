package com.example.librarymanagementsystem.dto.responseDTO.mapper;

import com.example.librarymanagementsystem.domain.Issue;
import com.example.librarymanagementsystem.dto.responseDTO.BookHistoryResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.IssueResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class IssueResponseMapper {

    public IssueResponseDTO mapIssueToIssueResponseDTO(final Issue issue) {
        IssueResponseDTO issueResponseDTO = new IssueResponseDTO();
        issueResponseDTO.setId(issue.getId());
        issueResponseDTO.setBookList(issue.getBookList());
        issueResponseDTO.setIssuedDate(issue.getIssuedDate().toString());
        issueResponseDTO.setDueDate(issue.getDueDate().toString());
        issueResponseDTO.setMemberUsername(issue.getMemberUsername());
        issueResponseDTO.setIssueStatus(issue.getIssueStatus().toString());
        return issueResponseDTO;
    }

    public BookHistoryResponseDTO mapIssueToBookHistoryResponseDTO(final Issue issue) {
        BookHistoryResponseDTO bookHistoryResponseDTO = new BookHistoryResponseDTO();
        bookHistoryResponseDTO.setIssueId(issue.getId());
        bookHistoryResponseDTO.setBookList(issue.getBookList());
        bookHistoryResponseDTO.setIssuedDate(issue.getIssuedDate().toString());
        bookHistoryResponseDTO.setDueDate(issue.getDueDate().toString());
        bookHistoryResponseDTO.setStatus(issue.getIssueStatus().toString());
        bookHistoryResponseDTO.setReturnedDate(issue.getReturnedDate().toString());
        return bookHistoryResponseDTO;
    }
}
