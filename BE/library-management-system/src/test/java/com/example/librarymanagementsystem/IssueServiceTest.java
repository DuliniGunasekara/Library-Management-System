package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.constants.BookStatus;
import com.example.librarymanagementsystem.constants.IssueStatus;
import com.example.librarymanagementsystem.domain.Book;
import com.example.librarymanagementsystem.domain.Issue;
import com.example.librarymanagementsystem.dto.responseDTO.BookHistoryResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.mapper.IssueResponseMapper;
import com.example.librarymanagementsystem.repositories.IssueRepository;
import com.example.librarymanagementsystem.services.IssueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IssueServiceTest {

    @InjectMocks
    private IssueService issueService;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private IssueResponseMapper issueResponseMapper;

    @BeforeEach
    public void setUp(){
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(securityContext.getAuthentication().getName()).thenReturn("dulini");
    }
    @Test
    public void getBookHistoryServiceTest(){
        Book book1 = new Book()
                .setId("636e157ca29f183870443af1")
                .setName("Pride and Premeditation")
                .setBookStatus(BookStatus.AVAILABLE)
                .setAuthor("Tirzah Price")
                .setIsbnNumber("ISBN1234");

        Book book2 = new Book()
                .setId("636e1eec65c50c2dda6422a8")
                .setBookStatus(BookStatus.AVAILABLE)
                .setName("Wuthering Heights")
                .setAuthor("Emily Bronte")
                .setIsbnNumber("ISBN1234");

        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);


        List<Issue> issueList = new ArrayList<>();
        Issue issue1 = new Issue();
        issue1.setId("1");
        issue1.setMemberUsername("dulini");
        issue1.setBookList(bookList);
        issue1.setIssueStatus(IssueStatus.RETURNED);
        issue1.setIssuedDate(LocalDate.of(2022,05,04));
        issue1.setDueDate(LocalDate.of(2022,05,11));
        issue1.setReturnedDate(LocalDate.of(2022,05,10));

        Issue issue2 = new Issue();
        issue2.setId("1");
        issue2.setMemberUsername("dulini");
        issue2.setBookList(bookList);
        issue2.setIssueStatus(IssueStatus.RETURNED);
        issue2.setIssuedDate(LocalDate.of(2022,05,04));
        issue2.setDueDate(LocalDate.of(2022,05,11));
        issue2.setReturnedDate(LocalDate.of(2022,05,10));

        issueList.add(issue1);
        issueList.add(issue2);

        when(issueRepository.findAllByMemberUsername(issue1.getMemberUsername())).thenReturn(issueList);

        List<BookHistoryResponseDTO> bookHistoryResponseDTOList = issueService.getBookHistoryService();

        assertEquals(2,bookHistoryResponseDTOList.size());
    }
}
