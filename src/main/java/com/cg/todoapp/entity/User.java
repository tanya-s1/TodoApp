package com.cg.todoapp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the Todo application.
 * Contains username, password, and the list of Todo items associated with this user.
 */
public class User {
    // Username uniquely identifying the user
    private String username;
    
    // Password used for authenticating the user
    private String password;
    
    // List of Todo tasks belonging to this user
    private List<Todo> todos;

    /**
     * Constructor to initialize a User with username and password.
     * Also initializes an empty list for Todos.
     *
     * @param username User's unique username
     * @param password User's password
     */
    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
        setTodos(new ArrayList<>()); // Initialize todos list as empty
    }

    /**
     * Gets the username of the user.
     *
     * @return username string
     */
    public String getUsername() { return username; }

    /**
     * Sets the username of the user.
     *
     * @param username username string to set
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Gets the password of the user.
     *
     * @return password string
     */
    public String getPassword() { return password; }

    /**
     * Sets the password of the user.
     *
     * @param password password string to set
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Gets the list of Todo items for the user.
     *
     * @return List of Todo objects
     */
    public List<Todo> getTodos() { return todos; }

    /**
     * Sets the list of Todo items for the user.
     *
     * @param todos List of Todo objects to set
     */
    public void setTodos(List<Todo> todos) { this.todos = todos; }
}
