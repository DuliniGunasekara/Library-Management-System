package com.example.librarymanagementsystem.controllers;


import com.example.librarymanagementsystem.domain.Issue;
import com.example.librarymanagementsystem.dto.requestDTO.IssueRequestDTO;
import com.example.librarymanagementsystem.dto.responseDTO.BookHistoryResponseDTO;
import com.example.librarymanagementsystem.dto.responseDTO.IssueResponseDTO;
import com.example.librarymanagementsystem.services.IssueService;
import com.example.librarymanagementsystem.util.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.List;

@RestController
@RequestMapping("/api/issue")
public class IssueController {

    private static final Logger logger = LoggerFactory.getLogger(IssueController.class);

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }


    @PostMapping
    public ResponseEntity<IssueResponseDTO> issueBooks(@RequestBody final IssueRequestDTO issueRequestDTO){
        logger.info("In issueBooks controller");

       if(!ValidateRequest.validateIssueRequestDTO(issueRequestDTO)){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
       IssueResponseDTO issueResponseDTO = issueService.issueBooksService(issueRequestDTO);
       if(issueResponseDTO == null){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
       return new ResponseEntity<>(issueResponseDTO,HttpStatus.CREATED);
    }

    @GetMapping("/history")
    public ResponseEntity<List<BookHistoryResponseDTO>> getBookHistory(){
        logger.info("In getBookHistory controller");


        List<BookHistoryResponseDTO> bookHistoryResponseDTOList = issueService.getBookHistoryService();
        if(bookHistoryResponseDTOList == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bookHistoryResponseDTOList,HttpStatus.OK);
    }
}
