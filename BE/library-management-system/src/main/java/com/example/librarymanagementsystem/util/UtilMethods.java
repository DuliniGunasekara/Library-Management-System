package com.example.librarymanagementsystem.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UtilMethods {

    public static LocalDate convertStringToDateFormat(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }
}
