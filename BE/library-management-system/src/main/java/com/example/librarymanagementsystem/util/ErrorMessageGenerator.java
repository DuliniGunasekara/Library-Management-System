package com.example.librarymanagementsystem.util;

public class ErrorMessageGenerator {

    public static String userNameNotFound(final String username){
        return "Username " + username + " not found!";
    }

    public static String userNotFound(final String id){
        return "User with id: " + id + " not found!";
    }

    public static String bookNotFound(final String id){
        return "Book with id:" + id + " not found!";
    }

    public static String bookAlreadyExisting(final String isbnNumber){
        return "Book with ISBN:" + isbnNumber + " is already existing!";
    }
}
