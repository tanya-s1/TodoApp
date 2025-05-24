package com.cg.todoapp.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.cg.todoapp.entity.Priority;

public class ValidatorTest {

    @Test
    public void testValidTitle() {
        assertTrue(Validator.isValidTitle("Buy Milk"));
        assertFalse(Validator.isValidTitle(""));
        assertFalse(Validator.isValidTitle("   "));
        assertFalse(Validator.isValidTitle(null));
    }

    @Test
    public void testIsValidPriority() {
        assertTrue(Validator.isValidPriority("LOW"));
        assertTrue(Validator.isValidPriority("medium"));
        assertTrue(Validator.isValidPriority("HIGH"));
        assertFalse(Validator.isValidPriority("urgent"));
    }

    @Test
    public void testParsePriority() {
        assertEquals(Priority.HIGH, Validator.parsePriority("high"));
        assertEquals(Priority.MEDIUM, Validator.parsePriority("MEDIUM"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParsePriorityInvalid() {
        Validator.parsePriority("urgent"); // Invalid enum value
    }

    @Test
    public void testIsValidDateTime() {
        assertTrue(Validator.isValidDateTime("2025-12-31 23:59"));
        assertFalse(Validator.isValidDateTime("31-12-2025 23:59")); // Wrong format
        assertFalse(Validator.isValidDateTime("2025-12-31"));       // Missing time
    }

    @Test
    public void testParseDateTime() {
        String dateStr = "2025-05-25 14:00";
        LocalDateTime dt = Validator.parseDateTime(dateStr);
        assertEquals(2025, dt.getYear());
        assertEquals(5, dt.getMonthValue());
        assertEquals(25, dt.getDayOfMonth());
        assertEquals(14, dt.getHour());
        assertEquals(0, dt.getMinute());
    }

    @Test(expected = DateTimeParseException.class)
    public void testParseDateTimeInvalid() {
        Validator.parseDateTime("2025/12/31 10:00"); // Invalid format
    }

    @Test
    public void testGetFormatterPattern() {
        DateTimeFormatter formatter = Validator.getFormatter();
        String formatted = LocalDateTime.of(2025, 12, 31, 23, 59).format(formatter);
        assertEquals("2025-12-31 23:59", formatted);
    }
}
