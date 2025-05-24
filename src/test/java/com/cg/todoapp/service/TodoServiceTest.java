package com.cg.todoapp.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.cg.todoapp.dao.TodoDao;
import com.cg.todoapp.dao.TodoDaoImpl;
import com.cg.todoapp.entity.Priority;
import com.cg.todoapp.entity.Todo;
import com.cg.todoapp.entity.User;

public class TodoServiceTest {

    private static final String TEST_FILE = "user_todos_test.json";
    private TodoService todoService;
    private User user;

    @Before
    public void setUp() {
        new File(TEST_FILE).delete(); // Clean file before each test
        TodoDao todoDao = new TodoDaoImpl(TEST_FILE);
        todoService = new TodoService(todoDao);
        user = new User("john", "pass");
    }

    @After
    public void tearDown() {
        new File(TEST_FILE).delete(); // Clean file after each test
    }

    @Test
    public void testAddTodo() {
        todoService.addTodo(user, "Title", "Description", Priority.HIGH, LocalDateTime.now());
        List<Todo> todos = todoService.getAll(user);
        assertEquals(1, todos.size());
        assertEquals("Title", todos.get(0).getTitle());
    }

    @Test
    public void testMarkCompleted() {
        todoService.addTodo(user, "Task", "desc", Priority.MEDIUM, LocalDateTime.now());
        boolean marked = todoService.markCompleted(user, 1, true);
        assertTrue(marked);
        assertTrue(todoService.getAll(user).get(0).isCompleted());
    }

    @Test
    public void testDeleteTodo() {
        todoService.addTodo(user, "To Delete", "desc", Priority.LOW, LocalDateTime.now());
        assertTrue(todoService.deleteTodo(user, 1));
        assertEquals(0, todoService.getAll(user).size());
    }

    @Test
    public void testSearchTodos() {
        todoService.addTodo(user, "Grocery shopping", "Buy milk", Priority.LOW, LocalDateTime.now());
        List<Todo> results = todoService.searchTodos(user, "milk");
        assertEquals(1, results.size());
    }

    @Test
    public void testFilterByPriority() {
        todoService.addTodo(user, "Work", "code", Priority.HIGH, LocalDateTime.now());
        List<Todo> high = todoService.filterByPriority(user, Priority.HIGH);
        assertEquals(1, high.size());
        assertEquals("Work", high.get(0).getTitle());
    }

    @Test
    public void testFilterByCompletion() {
        todoService.addTodo(user, "Incomplete", "desc", Priority.LOW, LocalDateTime.now());
        todoService.addTodo(user, "Complete", "desc", Priority.LOW, LocalDateTime.now());
        todoService.markCompleted(user, 2, true);

        List<Todo> completed = todoService.filterByCompletion(user, true);
        List<Todo> incomplete = todoService.filterByCompletion(user, false);

        assertEquals(1, completed.size());
        assertEquals("Complete", completed.get(0).getTitle());

        assertEquals(1, incomplete.size());
        assertEquals("Incomplete", incomplete.get(0).getTitle());
    }

    @Test
    public void testFilterByDueDate() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime today = now.withHour(10);
        LocalDateTime tomorrow = now.plusDays(1).withHour(10);
        LocalDateTime nextWeek = now.plusDays(7).withHour(10);

        todoService.addTodo(user, "Today", "desc", Priority.MEDIUM, today);
        todoService.addTodo(user, "Tomorrow", "desc", Priority.MEDIUM, tomorrow);
        todoService.addTodo(user, "Next Week", "desc", Priority.MEDIUM, nextWeek);

        List<Todo> filtered = todoService.filterByDueDate(user, now.minusDays(1), now.plusDays(2));

        assertEquals(2, filtered.size());
        assertTrue(filtered.stream().anyMatch(todo -> todo.getTitle().equals("Today")));
        assertTrue(filtered.stream().anyMatch(todo -> todo.getTitle().equals("Tomorrow")));
    }

    @Test
    public void testSortByDueDate() {
        todoService.addTodo(user, "Task 1", "earlier", Priority.MEDIUM, LocalDateTime.now().plusDays(1));
        todoService.addTodo(user, "Task 2", "later", Priority.MEDIUM, LocalDateTime.now().plusDays(2));
        List<Todo> sorted = todoService.sortTodos(user, "duedate");
        assertEquals("Task 1", sorted.get(0).getTitle());
    }

    @Test
    public void testSortByPriority() {
        todoService.addTodo(user, "Low Priority", "desc", Priority.LOW, LocalDateTime.now());
        todoService.addTodo(user, "High Priority", "desc", Priority.HIGH, LocalDateTime.now());

        List<Todo> sorted = todoService.sortTodos(user, "priority");

        assertEquals("Low Priority", sorted.get(0).getTitle());  // Alphabetical by enum order
    }

    @Test
    public void testSortByCreatedAt() throws InterruptedException {
        todoService.addTodo(user, "First", "desc", Priority.MEDIUM, LocalDateTime.now());
        Thread.sleep(10); // slight delay for a different timestamp
        todoService.addTodo(user, "Second", "desc", Priority.MEDIUM, LocalDateTime.now());

        List<Todo> sorted = todoService.sortTodos(user, "createdat");

        assertEquals("First", sorted.get(0).getTitle());
        assertEquals("Second", sorted.get(1).getTitle());
    }
}
