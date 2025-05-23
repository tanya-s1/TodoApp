package com.cg.todoapp.dao;

import java.util.List;
import java.util.Optional;

import com.cg.todoapp.entity.Todo;
import com.cg.todoapp.entity.User;

public interface TodoDao {
    void addTodo(User user, Todo todo);
    List<Todo> getAll(User user);
    Optional<Todo> findById(User user, long id);
    void deleteById(User user, long id);
    void updateTodo(User user, Todo todo);
    long getNextId(User user);
}
