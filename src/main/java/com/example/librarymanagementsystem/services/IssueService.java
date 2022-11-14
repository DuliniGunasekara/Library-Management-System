package com.example.librarymanagementsystem.services;

import com.example.librarymanagementsystem.constants.BookStatus;
import com.example.librarymanagementsystem.domain.Book;
import com.example.librarymanagementsystem.domain.Issue;
import com.example.librarymanagementsystem.domain.Member;
import com.example.librarymanagementsystem.dto.requestDTO.IssueRequestDTO;
import com.example.librarymanagementsystem.dto.requestDTO.mapper.IssueRequestMapper;
import com.example.librarymanagementsystem.dto.responseDTO.BookHistoryResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.IssueResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.mapper.IssueResponseMapper;
import com.example.librarymanagementsystem.repositories.BookRepository;
import com.example.librarymanagementsystem.repositories.IssueRepository;
import com.example.librarymanagementsystem.repositories.MemberRepository;
import com.example.librarymanagementsystem.util.ErrorMessageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class IssueService {

    private static final Logger logger = LoggerFactory.getLogger(IssueService.class);

    private final IssueRepository issueRepository;

    private final BookRepository bookRepository;

    private final IssueRequestMapper issueRequestMapper;
    private final IssueResponseMapper issueResponseMapper;

    private final BookService bookService;

    private final UserService userService;
    private final MemberRepository memberRepository;

    public IssueService(IssueRepository issueRepository, BookRepository bookRepository, IssueRequestMapper issueRequestMapper, IssueResponseMapper issueResponseMapper, BookService bookService, UserService userService, MemberRepository memberRepository) {
        this.issueRepository = issueRepository;
        this.bookRepository = bookRepository;
        this.issueRequestMapper = issueRequestMapper;
        this.issueResponseMapper = issueResponseMapper;
        this.bookService = bookService;
        this.userService = userService;
        this.memberRepository = memberRepository;
    }


    public IssueResponseDTO issueBooksService(final IssueRequestDTO issueRequestDTO) {
        logger.info("In issueBooksService method");

        Member member = userService.getExistingUser(issueRequestDTO.getMemberUsername());

        if (member != null && member.isEligible()) {
            member.setEligible(false);

        } else {
            logger.error(ErrorMessageGenerator.memberIsNotEligible(issueRequestDTO.getMemberUsername()));
            return null;
        }

        List<Book> bookList = issueRequestDTO.getBookList().stream().map(id -> bookRepository.findBookById(id).orElse(new Book())).filter(book -> book.getId() != null && book.getBookStatus().equals(BookStatus.AVAILABLE)).toList();

        if (bookList.isEmpty()) {
            logger.error(ErrorMessageGenerator.requestedBooksAreNotAvailable());
            return null;
        }
        memberRepository.save(member);
        Issue newIssue = issueRepository.save(issueRequestMapper.mapIssueRequestDTOtoIssue(issueRequestDTO, bookList));
        bookList.forEach(bookService::updateBookAvailability);
        return issueResponseMapper.mapIssueToIssueResponseDTO(newIssue);
    }

    public List<BookHistoryResponseDTO> getBookHistoryService() {
        logger.info("In getBookHistoryService method");

        List<Issue> issueList = issueRepository.findAllByMemberUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (issueList.isEmpty()) {
            logger.error(ErrorMessageGenerator.bookHistoryNotFound());
            return Collections.emptyList();
        }
        return issueList.stream().map(issueResponseMapper::mapIssueToBookHistoryResponseDTO).toList();
    }
}
