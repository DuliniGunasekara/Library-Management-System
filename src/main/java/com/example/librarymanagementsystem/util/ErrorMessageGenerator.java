package com.example.librarymanagementsystem.util;

import com.example.librarymanagementsystem.domain.Book;

import java.util.List;

public class ErrorMessageGenerator {

    private ErrorMessageGenerator(){}

    public static String userNameNotFound(final String username) {
        return "Username " + username + " not found!";
    }

    public static String userNotFound(final String id) {
        return "User with id: " + id + " not found!";
    }

    public static String bookNotFound(final String id) {
        return "Book with id:" + id + " not found!";
    }

    public static String bookAlreadyExisting(final String isbnNumber) {
        return "Book with ISBN:" + isbnNumber + " is already existing!";
    }

    public static String memberIsNotEligible(final String username) {
        return "Member with username:" + username + " is not eligible!";
    }

    public static String memberUsernameAlreadyExisting(final String username) {
        return "Member with username:" + username + " already existing!";
    }

    public static String requestedBooksAreNotAvailable(List<Book> bookList) {

        List<String> bookNames = bookList.stream().map(Book::getName).toList();
        return "Books: "+bookNames+" are not available!";
    }

    public static String bookHistoryNotFound() {
        return "Book history not found!";
    }

    public static String usernameNotAvailable() {
        return "Username not available!";
    }

    public static String bookISBNisExisting() {
        return "Book ISBN is existing";
    }

    public static String invalidRequestBody(){ return "Invalid request body!";}
    public static String invalidRequest(){ return "Invalid request!";}
    public static String failedToAddResource(){ return "Failed to add resource!";}
    public static String failedToEditResource(){ return "Failed to edit resource!";}
    public static String internalServerError(){ return "Internal Server error";}
    public static String newPasswordHasUsedBefore(){ return "New Password has used before";}
    public static String oldPasswordNotMatch(){ return "Old password is incorrect";}
}
