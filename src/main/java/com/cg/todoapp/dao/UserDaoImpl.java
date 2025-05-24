package com.cg.todoapp.dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cg.todoapp.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Implementation of UserDao interface to manage User data persistence.
 * Stores user information in a JSON file using Gson for serialization.
 */
public class UserDaoImpl implements UserDao {

    // Gson instance to handle JSON serialization and deserialization
    private final Gson gson = new Gson();

    // File name where user data is stored
    private String FILE_NAME;

    /**
     * Constructor to initialize the DAO with the file name for storage.
     *
     * @param FILE_NAME JSON file to persist user data
     */
    public UserDaoImpl(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    /**
     * Loads the list of users from the JSON file.
     * Returns an empty list if file does not exist or an error occurs.
     *
     * @return List of User objects loaded from file
     */
    private List<User> loadUsers() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        try (Reader reader = new FileReader(file)) {
            Type userListType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(reader, userListType);
            return users != null ? users : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Saves the given list of users back to the JSON file.
     *
     * @param users List of User objects to save
     */
    private void saveUsers(List<User> users) {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a user with the given username exists.
     * Case-insensitive comparison is used.
     *
     * @param username Username to check existence for
     * @return true if user exists, false otherwise
     */
    @Override
    public boolean exists(String username) {
        return loadUsers().stream()
                .anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    /**
     * Saves a new user or updates an existing user's information.
     * If user with the same username exists, it is replaced with the new user data.
     *
     * @param user User object to save or update
     */
    @Override
    public void save(User user) {
        List<User> users = loadUsers();

        // Remove existing user with same username (if any)
        Optional<User> existing = users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(user.getUsername()))
                .findFirst();

        existing.ifPresent(users::remove);
        users.add(user);
        saveUsers(users);
    }

    /**
     * Finds and returns a user by username.
     * Case-insensitive search.
     *
     * @param username Username to search for
     * @return Optional containing User if found, empty otherwise
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return loadUsers().stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    /**
     * Authenticates a user by matching username and password.
     * Case-insensitive username check, password is case-sensitive.
     *
     * @param username Username for authentication
     * @param password Password for authentication
     * @return true if credentials match an existing user, false otherwise
     */
    @Override
    public boolean authenticate(String username, String password) {
        return loadUsers().stream()
                .anyMatch(user -> user.getUsername().equalsIgnoreCase(username)
                        && user.getPassword().equals(password));
    }
}
