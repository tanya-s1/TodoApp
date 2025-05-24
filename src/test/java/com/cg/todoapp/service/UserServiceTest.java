package com.cg.todoapp.service;

import com.cg.todoapp.dao.UserDao;
import com.cg.todoapp.dao.UserDaoImpl;
import com.cg.todoapp.entity.User;
import org.junit.*;

import java.io.File;

import static org.junit.Assert.*;

public class UserServiceTest {

    private static final String TEST_FILE = "users_test.json";
    private UserService userService;

    @Before
    public void setUp() {
        new File(TEST_FILE).delete(); // Clean file
        UserDao userDao = new UserDaoImpl(TEST_FILE);
        userService = new UserService(userDao);
    }

    @After
    public void tearDown() {
        new File(TEST_FILE).delete(); // Clean up
    }

    @Test
    public void testRegisterAndLogin() {
        assertTrue(userService.register("alice", "pass123"));
        assertTrue(userService.login("alice", "pass123"));
        assertEquals("alice", userService.getCurrentUser().getUsername());
    }

    @Test
    public void testDuplicateRegistrationFails() {
        userService.register("bob", "123");
        assertFalse(userService.register("bob", "456"));
    }

    @Test
    public void testLoginFailsForInvalid() {
        assertFalse(userService.login("noUser", "wrong"));
    }

    @Test
    public void testLogout() {
        userService.register("eve", "1234");
        userService.login("eve", "1234");
        userService.logout();
        assertFalse(userService.isLoggedIn());
    }
}
