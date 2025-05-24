package com.cg.todoapp.service;

import java.util.Optional;

import com.cg.todoapp.dao.UserDao;
import com.cg.todoapp.entity.User;

/**
 * Service class that handles user-related operations such as
 * registration, login, logout, and session management.
 * Acts as a bridge between the DAO layer and application logic
 * related to users.
 */
public class UserService {
    // DAO layer used to manage user data persistence and retrieval
    private final UserDao userDao;

    // Holds the currently logged-in user; null if no user is logged in
    private User currentUser;

    /**
     * Constructor to initialize the service with a UserDao implementation.
     *
     * @param userDao DAO object to perform CRUD operations for User data.
     */
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Registers a new user with the provided username and password.
     * Checks for username uniqueness before saving.
     *
     * @param username The desired username for registration
     * @param password The password for the new user
     * @return false if username already exists; true if registration is successful
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
     * Attempts to log in a user with given credentials.
     * If authentication succeeds, sets currentUser to the logged-in user.
     *
     * @param username Username input for login
     * @param password Password input for login
     * @return true if authentication is successful; false otherwise
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
     * Logs out the current user by clearing the currentUser reference.
     */
    public void logout() {
        currentUser = null;
    }

    /**
     * Returns the currently logged-in user.
     *
     * @return User object of the current user; null if no user is logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return true if a user session is active; false otherwise
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
