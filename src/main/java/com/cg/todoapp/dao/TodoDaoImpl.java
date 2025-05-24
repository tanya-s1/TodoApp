package com.cg.todoapp.dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.cg.todoapp.entity.Todo;
import com.cg.todoapp.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Implementation of the TodoDao interface that stores user-specific todos
 * using a JSON file as the persistent medium.
 * Uses Gson for JSON serialization and deserialization.
 */
public class TodoDaoImpl implements TodoDao {

    private final Gson gson = new Gson();
    private final String FILE_NAME;

    /**
     * Constructor to initialize the JSON file path used for data storage.
     *
     * @param FILE_NAME the name/path of the JSON file used for persistence
     */
    public TodoDaoImpl(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    /**
     * Loads the complete map of usernames to their list of Todo objects
     * from the JSON file. If the file doesn't exist or an error occurs,
     * an empty map is returned.
     *
     * @return a Map where each key is a username and the value is a list of Todo objects
     */
    private Map<String, List<Todo>> loadTodoMap() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new HashMap<>();

        try (Reader reader = new FileReader(file)) {
            Type mapType = new TypeToken<Map<String, List<Todo>>>() {}.getType();
            Map<String, List<Todo>> data = gson.fromJson(reader, mapType);
            return data != null ? data : new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    /**
     * Saves the provided map of usernames and their respective Todo lists
     * back into the JSON file for persistence.
     *
     * @param todoMap the map containing user-specific Todo lists
     */
    private void saveTodoMap(Map<String, List<Todo>> todoMap) {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            gson.toJson(todoMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new Todo item for the given user by appending it to their
     * existing list and saving the updated map to file.
     *
     * @param user the user to whom the Todo belongs
     * @param todo the Todo item to be added
     */
    @Override
    public void addTodo(User user, Todo todo) {
        Map<String, List<Todo>> todoMap = loadTodoMap();
        List<Todo> userTodos = todoMap.getOrDefault(user.getUsername(), new ArrayList<>());
        userTodos.add(todo);
        todoMap.put(user.getUsername(), userTodos);
        saveTodoMap(todoMap);
    }

    /**
     * Retrieves all Todo items associated with the given user.
     *
     * @param user the user whose todos are being retrieved
     * @return a list of Todo items
     */
    @Override
    public List<Todo> getAll(User user) {
        return new ArrayList<>(loadTodoMap().getOrDefault(user.getUsername(), new ArrayList<>()));
    }

    /**
     * Finds a specific Todo item by its ID for the specified user.
     *
     * @param user the user who owns the Todo
     * @param id the ID of the Todo to be retrieved
     * @return an Optional containing the Todo if found, otherwise empty
     */
    @Override
    public Optional<Todo> findById(User user, long id) {
        return loadTodoMap().getOrDefault(user.getUsername(), new ArrayList<>())
                .stream()
                .filter(todo -> todo.getId() == id)
                .findFirst();
    }

    /**
     * Deletes a Todo item with the specified ID from the user's list.
     *
     * @param user the user whose Todo is to be deleted
     * @param id the ID of the Todo to delete
     */
    @Override
    public void deleteById(User user, long id) {
        Map<String, List<Todo>> todoMap = loadTodoMap();
        List<Todo> userTodos = todoMap.getOrDefault(user.getUsername(), new ArrayList<>());
        userTodos.removeIf(todo -> todo.getId() == id);
        todoMap.put(user.getUsername(), userTodos);
        saveTodoMap(todoMap);
    }

    /**
     * Updates an existing Todo item for the given user by replacing it
     * in the list and saving the changes to the file.
     *
     * @param user the user who owns the Todo
     * @param updatedTodo the updated Todo object
     */
    @Override
    public void updateTodo(User user, Todo updatedTodo) {
        Map<String, List<Todo>> todoMap = loadTodoMap();
        List<Todo> userTodos = todoMap.getOrDefault(user.getUsername(), new ArrayList<>());

        for (int i = 0; i < userTodos.size(); i++) {
            if (userTodos.get(i).getId() == updatedTodo.getId()) {
                userTodos.set(i, updatedTodo);
                break;
            }
        }

        todoMap.put(user.getUsername(), userTodos);
        saveTodoMap(todoMap);
    }

    /**
     * Generates the next unique ID for a new Todo item by finding
     * the maximum current ID in the user's list and incrementing it.
     *
     * @param user the user for whom the new ID is generated
     * @return the next available ID for the user's Todo
     */
    @Override
    public long getNextId(User user) {
        return loadTodoMap().getOrDefault(user.getUsername(), new ArrayList<>())
                .stream()
                .mapToLong(Todo::getId)
                .max()
                .orElse(0L) + 1;
    }
}
