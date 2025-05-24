package com.cg.todoapp.util;

import java.time.LocalDateTime;

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
    }

    @Test
    public void testPriorityParsing() {
        assertTrue(Validator.isValidPriority("LOW"));
        assertEquals(Priority.HIGH, Validator.parsePriority("high"));
    }

    @Test
    public void testDateTimeParsing() {
        String dateStr = "2025-05-25 14:00";
        assertTrue(Validator.isValidDateTime(dateStr));
        LocalDateTime dt = Validator.parseDateTime(dateStr);
        assertEquals(2025, dt.getYear());
    }
}
