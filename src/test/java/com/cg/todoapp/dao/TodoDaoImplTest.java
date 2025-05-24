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

    private TodoDaoImpl todoDao;
    private static final String TEST_FILE = "user_todos_test.json";
    private User testUser;

    @Before
    public void setUp() {
        todoDao = new TodoDaoImpl(TEST_FILE);
        testUser = new User("john", "pass");
        new File(TEST_FILE).delete(); // Clean file
    }

    @After
    public void tearDown() {
        new File(TEST_FILE).delete(); // Cleanup
    }

    @Test
    public void testAddAndGetAllTodos() {
        Todo todo = new Todo(1, "Task", "Desc", Priority.MEDIUM, LocalDateTime.now());
        todoDao.addTodo(testUser, todo);

        List<Todo> todos = todoDao.getAll(testUser);
        assertEquals(1, todos.size());
        assertEquals("Task", todos.get(0).getTitle());
    }

    @Test
    public void testDeleteTodo() {
        Todo todo = new Todo(1, "DeleteMe", "desc", Priority.LOW, LocalDateTime.now());
        todoDao.addTodo(testUser, todo);
        todoDao.deleteById(testUser, 1);
        assertEquals(0, todoDao.getAll(testUser).size());
    }

    @Test
    public void testUpdateTodo() {
        Todo todo = new Todo(1, "Old", "desc", Priority.LOW, LocalDateTime.now());
        todoDao.addTodo(testUser, todo);
        todo.setTitle("Updated");
        todoDao.updateTodo(testUser, todo);

        Todo updated = todoDao.findById(testUser, 1).orElse(null);
        assertNotNull(updated);
        assertEquals("Updated", updated.getTitle());
    }

    @Test
    public void testGetNextId() {
        assertEquals(1, todoDao.getNextId(testUser));
        todoDao.addTodo(testUser, new Todo(1, "Test", "", Priority.HIGH, LocalDateTime.now()));
        assertEquals(2, todoDao.getNextId(testUser));
    }
}
