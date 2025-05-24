package com.cg.todoapp.util;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.cg.todoapp.entity.Priority;

public class ValidatorTest {

    /**
     * Test to verify the validation of task titles.
     * Checks that a non-empty title returns true.
     * Checks that an empty string returns false.
     */
    @Test
    public void testValidTitle() {
        assertTrue(Validator.isValidTitle("Buy Milk"));
        assertFalse(Validator.isValidTitle(""));
    }

    /**
     * Test priority string validation and parsing.
     * Verifies that valid priority strings (case insensitive) are recognized.
     * Checks that parsing returns the correct Priority enum value.
     */
    @Test
    public void testPriorityParsing() {
        assertTrue(Validator.isValidPriority("LOW"));
        assertEquals(Priority.HIGH, Validator.parsePriority("high"));
    }

    /**
     * Test date-time string validation and parsing.
     * Validates the format of the date-time string.
     * Parses the string into a LocalDateTime object and verifies the year component.
     */
    @Test
    public void testDateTimeParsing() {
        String dateStr = "2025-05-25 14:00";
        assertTrue(Validator.isValidDateTime(dateStr));
        LocalDateTime dt = Validator.parseDateTime(dateStr);
        assertEquals(2025, dt.getYear());
    }
}
