package com.example.librarymanagementsystem.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseError {
    private int httpStatusCode;
    private String errorMsg;
}
