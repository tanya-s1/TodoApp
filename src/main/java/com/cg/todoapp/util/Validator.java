package com.cg.todoapp.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.cg.todoapp.entity.Priority;

/**
 * Utility class providing validation and parsing methods for
 * Todo app input fields such as title, priority, and date-time strings.
 */
public class Validator {

    // Defines the date-time format pattern to be used for parsing and formatting
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Validates that the given title is not null, not empty, and not just whitespace.
     *
     * @param title The title string to validate
     * @return true if title is valid; false otherwise
     */
    public static boolean isValidTitle(String title) {
        return title != null && !title.trim().isEmpty();
    }

    /**
     * Validates that the provided string corresponds to a valid Priority enum value.
     * Comparison is case-insensitive.
     *
     * @param priority The priority string to validate
     * @return true if priority matches one of the Priority enum constants; false otherwise
     */
    public static boolean isValidPriority(String priority) {
        try {
            Priority.valueOf(priority.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            // Thrown if the string does not match any enum constant
            return false;
        }
    }

    /**
     * Checks whether the input string can be successfully parsed into a LocalDateTime
     * using the specified DATE_TIME_FORMATTER.
     *
     * @param input The date-time string to validate
     * @return true if parsing succeeds; false if a DateTimeParseException is thrown
     */
    public static boolean isValidDateTime(String input) {
        try {
            parseDateTime(input);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Parses the given date-time string into a LocalDateTime object
     * using the predefined DATE_TIME_FORMATTER pattern.
     *
     * @param input The date-time string to parse
     * @return LocalDateTime object representing the parsed date and time
     * @throws DateTimeParseException if the input does not match the format
     */
    public static LocalDateTime parseDateTime(String input) {
        return LocalDateTime.parse(input.trim(), DATE_TIME_FORMATTER);
    }

    /**
     * Parses the input string to a Priority enum value.
     * Trims whitespace and converts to uppercase for matching.
     *
     * @param input The priority string to parse
     * @return Corresponding Priority enum constant
     * @throws IllegalArgumentException if the input does not match any Priority constant
     */
    public static Priority parsePriority(String input) {
        return Priority.valueOf(input.trim().toUpperCase());
    }

    /**
     * Returns the DateTimeFormatter instance used for date-time parsing and formatting.
     *
     * @return The DateTimeFormatter with pattern "yyyy-MM-dd HH:mm"
     */
    public static DateTimeFormatter getFormatter() {
        return DATE_TIME_FORMATTER;
    }
}
