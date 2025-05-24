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

    private UserDaoImpl userDao;
    private static final String TEST_FILE = "users_test.json";

    @Before
    public void setUp() {
        // override production file
        userDao = new UserDaoImpl(TEST_FILE);
        new File(TEST_FILE).delete(); // Clean before test
    }

    @After
    public void tearDown() {
        new File(TEST_FILE).delete(); // Clean after test
    }

    @Test
    public void testSaveAndFindUser() {
        User user = new User("john", "pass123");
        userDao.save(user);

        Optional<User> result = userDao.findByUsername("john");
        assertTrue(result.isPresent());
        assertEquals("john", result.get().getUsername());
    }

    @Test
    public void testAuthenticateSuccess() {
        User user = new User("emma", "secret");
        userDao.save(user);
        assertTrue(userDao.authenticate("emma", "secret"));
    }

    @Test
    public void testAuthenticateFail() {
        assertFalse(userDao.authenticate("noUser", "wrong"));
    }

    @Test
    public void testUserExists() {
        userDao.save(new User("exists", "123"));
        assertTrue(userDao.exists("exists"));
        assertFalse(userDao.exists("nonexistent"));
    }
}
