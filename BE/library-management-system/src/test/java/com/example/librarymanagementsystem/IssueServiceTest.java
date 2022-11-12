package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.repositories.IssueRepository;
import com.example.librarymanagementsystem.services.IssueService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IssueServiceTest {

    @InjectMocks
    private IssueService issueService;

    @Mock
    private IssueRepository issueRepository;
}
