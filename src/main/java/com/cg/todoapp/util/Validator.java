package com.cg.todoapp.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.cg.todoapp.entity.Priority;

public class Validator {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isValidTitle(String title) {
        return title != null && !title.trim().isEmpty();
    }

    public static boolean isValidPriority(String priority) {
        try {
            Priority.valueOf(priority.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isValidDateTime(String input) {
        try {
            parseDateTime(input);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static LocalDateTime parseDateTime(String input) {
        return LocalDateTime.parse(input.trim(), DATE_TIME_FORMATTER);
    }

    public static Priority parsePriority(String input) {
        return Priority.valueOf(input.trim().toUpperCase());
    }

    public static DateTimeFormatter getFormatter() {
        return DATE_TIME_FORMATTER;
    }
}
