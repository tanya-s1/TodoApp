package com.cg.todoapp.service;

import com.cg.todoapp.dao.UserDao;
import com.cg.todoapp.dao.UserDaoImpl;
import com.cg.todoapp.entity.User;
import org.junit.*;

import java.io.File;

import static org.junit.Assert.*;

public class UserServiceTest {

    // Test file used to store user data separately from production
    private static final String TEST_FILE = "users_test.json";

    // Instance of UserService under test
    private UserService userService;

    /**
     * Setup method run before each test.
     * Deletes the test file to ensure a clean slate.
     * Initializes UserDaoImpl with the test file.
     * Creates the UserService instance using the DAO.
     */
    @Before
    public void setUp() {
        new File(TEST_FILE).delete(); // Clean file
        UserDao userDao = new UserDaoImpl(TEST_FILE);
        userService = new UserService(userDao);
    }

    /**
     * Tear down method run after each test.
     * Deletes the test file to remove test artifacts.
     */
    @After
    public void tearDown() {
        new File(TEST_FILE).delete(); // Clean up
    }

    /**
     * Test user registration and login functionality.
     * Registers a new user and asserts registration success.
     * Logs in the same user and asserts login success.
     * Verifies the current logged-in user's username.
     */
    @Test
    public void testRegisterAndLogin() {
        assertTrue(userService.register("alice", "pass123"));
        assertTrue(userService.login("alice", "pass123"));
        assertEquals("alice", userService.getCurrentUser().getUsername());
    }

    /**
     * Test to ensure duplicate user registration is prevented.
     * Registers a user, then attempts to register the same username again.
     * Asserts that the second registration fails.
     */
    @Test
    public void testDuplicateRegistrationFails() {
        userService.register("bob", "123");
        assertFalse(userService.register("bob", "456"));
    }

    /**
     * Test login failure for non-existent users or invalid credentials.
     * Attempts to login with invalid username and password.
     * Asserts that login returns false.
     */
    @Test
    public void testLoginFailsForInvalid() {
        assertFalse(userService.login("noUser", "wrong"));
    }

    /**
     * Test logout functionality.
     * Registers and logs in a user.
     * Calls logout and asserts that the user is no longer logged in.
     */
    @Test
    public void testLogout() {
        userService.register("eve", "1234");
        userService.login("eve", "1234");
        userService.logout();
        assertFalse(userService.isLoggedIn());
    }
}
