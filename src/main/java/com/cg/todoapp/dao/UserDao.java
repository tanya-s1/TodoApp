package com.cg.todoapp.dao;

import java.util.Optional;

import com.cg.todoapp.entity.User;

/**
 * Interface for user-related data access operations.
 * Provides methods to manage and authenticate users in the system.
 */
public interface UserDao {

    /**
     * Checks whether a user with the specified username exists.
     *
     * @param username the username to check
     * @return true if the user exists, false otherwise
     */
    boolean exists(String username);

    /**
     * Saves a new user to the data store.
     *
     * @param user the User object to save
     */
    void save(User user);

    /**
     * Finds and retrieves a user by their username.
     *
     * @param username the username of the user to find
     * @return an Optional containing the User if found, or empty if not
     */
    Optional<User> findByUsername(String username);

    /**
     * Authenticates a user based on the provided username and password.
     *
     * @param username the user's username
     * @param password the user's password
     * @return true if credentials are correct, false otherwise
     */
    boolean authenticate(String username, String password);
}
