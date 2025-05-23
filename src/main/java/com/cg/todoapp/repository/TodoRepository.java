package com.cg.todoapp.repository;

import java.util.List;
import java.util.Optional;

import com.cg.todoapp.entity.Todo;
import com.cg.todoapp.entity.User;

public class TodoRepository {

    public void addTodo(User user, Todo todo) {
        user.getTodos().add(todo);
    }

    public List<Todo> getTodos(User user) {
        return user.getTodos();
    }

    public Optional<Todo> findById(User user, long id) {
        return user.getTodos().stream()
                   .filter(todo -> todo.getId() == id)
                   .findFirst();
    }

    public void deleteById(User user, long id) {
        user.getTodos().removeIf(todo -> todo.getId() == id);
    }

    public void updateTodo(User user, Todo updatedTodo) {
        List<Todo> todos = user.getTodos();
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getId() == updatedTodo.getId()) {
                todos.set(i, updatedTodo);
                break;
            }
        }
    }

    public long getNextTodoId(User user) {
        return user.getTodos().stream()
                   .mapToLong(Todo::getId)
                   .max()
                   .orElse(0) + 1;
    }
}
