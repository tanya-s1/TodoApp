package com.cg.todoapp.dao;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

import com.cg.todoapp.entity.Priority;
import com.cg.todoapp.entity.Todo;
import com.cg.todoapp.entity.User;

public class TodoDaoImplTest {

    // Instance of the DAO implementation to be tested
    private TodoDaoImpl todoDao;

    // Filename for test data storage
    private static final String TEST_FILE = "user_todos_test.json";

    // A sample user used across test methods
    private User testUser;

    /**
     * Sets up the test environment before each test case.
     * Initializes the TodoDaoImpl with a test file and creates a test user.
     * Also deletes the test file to ensure a clean slate before each test.
     */
    @Before
    public void setUp() {
        todoDao = new TodoDaoImpl(TEST_FILE);
        testUser = new User("john", "pass");
        new File(TEST_FILE).delete(); // Clean file before tests
    }

    /**
     * Cleans up after each test by deleting the test file.
     * Ensures no test data persists between test runs.
     */
    @After
    public void tearDown() {
        new File(TEST_FILE).delete(); // Cleanup test file after tests
    }

    /**
     * Tests adding a todo item and retrieving all todos for the user.
     * Verifies that the todo is successfully added and retrievable.
     */
    @Test
    public void testAddAndGetAllTodos() {
        Todo todo = new Todo(1, "Task", "Desc", Priority.MEDIUM, LocalDateTime.now());
        todoDao.addTodo(testUser, todo);

        List<Todo> todos = todoDao.getAll(testUser);
        assertEquals(1, todos.size());                  // Assert one todo exists
        assertEquals("Task", todos.get(0).getTitle());  // Assert title matches
    }

    /**
     * Tests deleting a todo by its ID.
     * Adds a todo, deletes it, then checks that the list is empty.
     */
    @Test
    public void testDeleteTodo() {
        Todo todo = new Todo(1, "DeleteMe", "desc", Priority.LOW, LocalDateTime.now());
        todoDao.addTodo(testUser, todo);
        todoDao.deleteById(testUser, 1);
        assertEquals(0, todoDao.getAll(testUser).size());  // Assert no todos remain
    }

    /**
     * Tests updating an existing todo.
     * Adds a todo, updates its title, then retrieves and checks updated value.
     */
    @Test
    public void testUpdateTodo() {
        Todo todo = new Todo(1, "Old", "desc", Priority.LOW, LocalDateTime.now());
        todoDao.addTodo(testUser, todo);
        todo.setTitle("Updated");
        todoDao.updateTodo(testUser, todo);

        Todo updated = todoDao.findById(testUser, 1).orElse(null);
        assertNotNull(updated);                          // Assert todo exists
        assertEquals("Updated", updated.getTitle());    // Assert title updated correctly
    }

    /**
     * Tests generation of the next unique todo ID for the user.
     * Initially expects 1, after adding a todo expects next ID to be incremented.
     */
    @Test
    public void testGetNextId() {
        assertEquals(1, todoDao.getNextId(testUser));  // Initially, next ID should be 1
        todoDao.addTodo(testUser, new Todo(1, "Test", "", Priority.HIGH, LocalDateTime.now()));
        assertEquals(2, todoDao.getNextId(testUser));  // After adding, next ID should be 2
    }
}
