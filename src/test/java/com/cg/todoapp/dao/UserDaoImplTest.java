package com.cg.todoapp.dao;

import java.io.File;
import java.util.Optional;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.cg.todoapp.entity.User;

public class UserDaoImplTest {

    // Instance of the UserDaoImpl to test user data operations
    private UserDaoImpl userDao;

    // Filename for storing user test data separately from production
    private static final String TEST_FILE = "users_test.json";

    /**
     * Sets up the test environment before each test case.
     * Instantiates UserDaoImpl with a test file.
     * Deletes the test file to ensure a clean environment before tests run.
     */
    @Before
    public void setUp() {
        // override production file with test file
        userDao = new UserDaoImpl(TEST_FILE);
        new File(TEST_FILE).delete(); // Clean before test
    }

    /**
     * Cleans up after each test case by deleting the test file.
     * Ensures no leftover data affects other tests.
     */
    @After
    public void tearDown() {
        new File(TEST_FILE).delete(); // Clean after test
    }

    /**
     * Tests saving a new user and then finding the user by username.
     * Asserts that the user is successfully saved and can be retrieved.
     */
    @Test
    public void testSaveAndFindUser() {
        User user = new User("john", "pass123");
        userDao.save(user);

        Optional<User> result = userDao.findByUsername("john");
        assertTrue(result.isPresent());               // Check user found
        assertEquals("john", result.get().getUsername());  // Check username matches
    }

    /**
     * Tests successful authentication with correct username and password.
     * Asserts that authenticate returns true when credentials are correct.
     */
    @Test
    public void testAuthenticateSuccess() {
        User user = new User("emma", "secret");
        userDao.save(user);
        assertTrue(userDao.authenticate("emma", "secret"));
    }

    /**
     * Tests authentication failure with non-existent user or wrong password.
     * Asserts that authenticate returns false for invalid credentials.
     */
    @Test
    public void testAuthenticateFail() {
        assertFalse(userDao.authenticate("noUser", "wrong"));
    }

    /**
     * Tests the exists method to verify if a username is present in the data store.
     * Saves a user and asserts exists returns true for that username,
     * and false for a username not present.
     */
    @Test
    public void testUserExists() {
        userDao.save(new User("exists", "123"));
        assertTrue(userDao.exists("exists"));        // User exists should return true
        assertFalse(userDao.exists("nonexistent"));  // Nonexistent user should return false
    }
}
