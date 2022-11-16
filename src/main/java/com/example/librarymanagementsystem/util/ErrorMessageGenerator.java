package com.example.librarymanagementsystem.util;

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

    public static String requestedBooksAreNotAvailable() {
        return "Requested books are not available!";
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
    public static String failedToAddResource(){ return "Failed to add resource!";}
    public static String failedToEditResource(){ return "Failed to edit resource!";}
}
