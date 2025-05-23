package entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ToDoTest {

    @Test
    void testToDoCreation() {
        ToDo todo = new ToDo(1, "Title", "Description", "High", LocalDate.of(2025, 6, 1));

        assertEquals(1, todo.getId());
        assertEquals("Title", todo.getTitle());
        assertEquals("Description", todo.getDescription());
        assertEquals("High", todo.getPriority());
        assertEquals(LocalDate.of(2025, 6, 1), todo.getDueDate());
        assertFalse(todo.isCompleted());
    }

    @Test
    void testSetCompleted() {
        ToDo todo = new ToDo(2, "Test", "Testing completed", "Medium", LocalDate.now());
        todo.setCompleted(true);
        assertTrue(todo.isCompleted());
    }
}
