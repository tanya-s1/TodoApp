package com.cg.todoapp.service;

import java.util.Optional;

import com.cg.todoapp.dao.UserDao;
import com.cg.todoapp.entity.User;

public class UserService {
    // DAO layer used to manage user data
    private final UserDao userDao;

    // Holds the currently logged-in user (null if no user is logged in)
    private User currentUser;

    // Constructor initializes the service with a UserDao implementation
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Registers a new user with the provided username and password.
     * Returns false if the username is already taken.
     */
    public boolean register(String username, String password) {
        if (userDao.exists(username)) {
            return false; // Username already taken
        }
        User newUser = new User(username, password);
        userDao.save(newUser);
        return true;
    }

    /**
     * Attempts to log in with the provided username and password.
     * If successful, sets the currentUser field.
     */
    public boolean login(String username, String password) {
        if (userDao.authenticate(username, password)) {
            Optional<User> user = userDao.findByUsername(username);
            user.ifPresent(value -> currentUser = value);
            return true;
        }
        return false;
    }

    /**
     * Logs out the current user by setting currentUser to null.
     */
    public void logout() {
        currentUser = null;
    }

    /**
     * Returns the currently logged-in user.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Checks whether a user is currently logged in.
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
