package com.cg.todoapp.dao;

import java.util.List;
import java.util.Optional;

import com.cg.todoapp.entity.Todo;
import com.cg.todoapp.entity.User;

/**
 * Interface for Todo Data Access Object.
 * Defines the operations for managing Todo items associated with specific users.
 */
public interface TodoDao {

    /**
     * Adds a new Todo item for the specified user.
     *
     * @param user the user to whom the Todo item belongs
     * @param todo the Todo item to be added
     */
    void addTodo(User user, Todo todo);

    /**
     * Retrieves all Todo items for the given user.
     *
     * @param user the user whose Todo list is to be fetched
     * @return a list of Todo items associated with the user
     */
    List<Todo> getAll(User user);

    /**
     * Finds a specific Todo item by its ID for the given user.
     *
     * @param user the user who owns the Todo
     * @param id the unique identifier of the Todo item
     * @return an Optional containing the Todo item if found, or empty otherwise
     */
    Optional<Todo> findById(User user, long id);

    /**
     * Deletes a specific Todo item by its ID for the given user.
     *
     * @param user the user who owns the Todo
     * @param id the unique identifier of the Todo to be deleted
     */
    void deleteById(User user, long id);

    /**
     * Updates an existing Todo item for the specified user.
     *
     * @param user the user who owns the Todo
     * @param todo the Todo item with updated details
     */
    void updateTodo(User user, Todo todo);

    /**
     * Retrieves the next available unique ID for a new Todo item for the specified user.
     * This is typically used to assign a unique identifier to new Todo entries.
     *
     * @param user the user for whom the next ID is generated
     * @return the next unique Todo ID
     */
    long getNextId(User user);
}

