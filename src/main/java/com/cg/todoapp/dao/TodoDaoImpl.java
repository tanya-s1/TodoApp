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

public class TodoDaoImpl implements TodoDao {

    private static final String FILE_NAME = "user_todos.json";
    private final Gson gson = new Gson();

    // Loads the complete map of user → list of todos
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

    // Saves the entire map back to file
    private void saveTodoMap(Map<String, List<Todo>> todoMap) {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            gson.toJson(todoMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTodo(User user, Todo todo) {
        Map<String, List<Todo>> todoMap = loadTodoMap();
        List<Todo> userTodos = todoMap.getOrDefault(user.getUsername(), new ArrayList<>());
        userTodos.add(todo);
        todoMap.put(user.getUsername(), userTodos);
        saveTodoMap(todoMap);
    }

    @Override
    public List<Todo> getAll(User user) {
        return new ArrayList<>(loadTodoMap().getOrDefault(user.getUsername(), new ArrayList<>()));
    }

    @Override
    public Optional<Todo> findById(User user, long id) {
        return loadTodoMap().getOrDefault(user.getUsername(), new ArrayList<>())
                .stream()
                .filter(todo -> todo.getId() == id)
                .findFirst();
    }

    @Override
    public void deleteById(User user, long id) {
        Map<String, List<Todo>> todoMap = loadTodoMap();
        List<Todo> userTodos = todoMap.getOrDefault(user.getUsername(), new ArrayList<>());
        userTodos.removeIf(todo -> todo.getId() == id);
        todoMap.put(user.getUsername(), userTodos);
        saveTodoMap(todoMap);
    }

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

    @Override
    public long getNextId(User user) {
        return loadTodoMap().getOrDefault(user.getUsername(), new ArrayList<>())
                .stream()
                .mapToLong(Todo::getId)
                .max()
                .orElse(0L) + 1;
    }
}
