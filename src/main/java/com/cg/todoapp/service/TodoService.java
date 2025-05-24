package com.cg.todoapp.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cg.todoapp.dao.TodoDao;
import com.cg.todoapp.entity.Priority;
import com.cg.todoapp.entity.Todo;
import com.cg.todoapp.entity.User;

/**
 * Service class that provides business logic for managing Todo items for users.
 * It acts as a bridge between the DAO layer and the application logic,
 * handling operations like add, update, delete, search, filter, and sort.
 */
public class TodoService {
    // DAO layer instance to manage persistence of Todo data
    private final TodoDao todoDao;

    /**
     * Constructor to initialize the service with a TodoDao implementation.
     *
     * @param todoDao DAO object to perform CRUD operations on Todo data.
     */
    public TodoService(TodoDao todoDao) {
        this.todoDao = todoDao;
    }

    /**
     * Adds a new Todo item for a specified user.
     * Generates a unique ID using the DAO and then saves the Todo.
     *
     * @param user The user to whom the Todo belongs
     * @param title Title of the Todo task
     * @param desc Description of the task
     * @param priority Priority level of the task
     * @param dueDateTime Deadline for the task
     */
    public void addTodo(User user, String title, String desc, Priority priority, LocalDateTime dueDateTime) {
        long id = todoDao.getNextId(user);
        Todo todo = new Todo(id, title, desc, priority, dueDateTime);
        todoDao.addTodo(user, todo);
    }

    /**
     * Retrieves all Todo items associated with the specified user.
     *
     * @param user The user whose Todos are to be fetched
     * @return List of all Todo items for the user
     */
    public List<Todo> getAll(User user) {
        return todoDao.getAll(user);
    }

    /**
     * Marks a specific Todo as completed or not completed.
     * Updates the Todo if found and returns success status.
     *
     * @param user The user owning the Todo
     * @param id The unique ID of the Todo item
     * @param completed The completion status to set
     * @return true if Todo was found and updated; false otherwise
     */
    public boolean markCompleted(User user, long id, boolean completed) {
        Optional<Todo> todoOpt = todoDao.findById(user, id);
        if (todoOpt.isPresent()) {
            Todo todo = todoOpt.get();
            todo.setCompleted(completed);
            todoDao.updateTodo(user, todo);
            return true;
        }
        return false;
    }

    /**
     * Deletes the Todo with the specified ID for the given user.
     *
     * @param user The user owning the Todo
     * @param id The ID of the Todo to delete
     * @return true if Todo was found and deleted; false otherwise
     */
    public boolean deleteTodo(User user, long id) {
        Optional<Todo> todoOpt = todoDao.findById(user, id);
        if (todoOpt.isPresent()) {
            todoDao.deleteById(user, id);
            return true;
        }
        return false;
    }

    /**
     * Searches the user's Todos for a keyword present in the title or description.
     * The search is case-insensitive.
     *
     * @param user The user whose Todos to search
     * @param keyword The keyword to look for in title or description
     * @return List of Todos matching the keyword search
     */
    public List<Todo> searchTodos(User user, String keyword) {
        return todoDao.getAll(user).stream()
            .filter(todo -> todo.getTitle().toLowerCase().contains(keyword.toLowerCase())
                         || todo.getDescription().toLowerCase().contains(keyword.toLowerCase()))
            .collect(Collectors.toList());
    }

    /**
     * Filters the user's Todos based on whether they are completed or not.
     *
     * @param user The user whose Todos to filter
     * @param completed The completion status to filter by
     * @return List of Todos filtered by completion status
     */
    public List<Todo> filterByCompletion(User user, boolean completed) {
        return todoDao.getAll(user).stream()
                .filter(todo -> todo.isCompleted() == completed)
                .collect(Collectors.toList());
    }

    /**
     * Filters the user's Todos by the specified priority level.
     *
     * @param user The user whose Todos to filter
     * @param priority The priority to filter by
     * @return List of Todos matching the specified priority
     */
    public List<Todo> filterByPriority(User user, Priority priority) {
        return todoDao.getAll(user).stream()
                .filter(todo -> todo.getPriority() == priority)
                .collect(Collectors.toList());
    }

    /**
     * Filters the user's Todos that have due dates within the specified date-time range (inclusive).
     *
     * @param user The user whose Todos to filter
     * @param from Start of the date-time range
     * @param to End of the date-time range
     * @return List of Todos with due dates in the given range
     */
    public List<Todo> filterByDueDate(User user, LocalDateTime from, LocalDateTime to) {
        return todoDao.getAll(user).stream()
            .filter(todo -> (todo.getDueDateTime().isEqual(from) || todo.getDueDateTime().isAfter(from)) &&
                            (todo.getDueDateTime().isEqual(to) || todo.getDueDateTime().isBefore(to)))
            .collect(Collectors.toList());
    }

    /**
     * Sorts the user's Todos by the specified field.
     * Supports sorting by priority, due date, or creation date.
     *
     * @param user The user whose Todos to sort
     * @param sortBy Field name to sort by ("priority", "duedate", or "createdat")
     * @return Sorted list of Todos based on the given field
     */
    public List<Todo> sortTodos(User user, String sortBy) {
        Comparator<Todo> comparator;

        switch (sortBy.toLowerCase()) {
            case "priority":
                comparator = Comparator.comparing(Todo::getPriority);
                break;
            case "duedate":
                comparator = Comparator.comparing(Todo::getDueDateTime);
                break;
            case "createdat":
            default:
                comparator = Comparator.comparing(Todo::getCreatedAt);
        }

        return todoDao.getAll(user).stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
