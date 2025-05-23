package com.cg.todoapp.dao;

import java.util.List;
import java.util.Optional;

import com.cg.todoapp.entity.Todo;
import com.cg.todoapp.entity.User;
import com.cg.todoapp.repository.TodoRepository;

public class TodoDaoImpl implements TodoDao {

    private final TodoRepository todoRepository;

    public TodoDaoImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public void addTodo(User user, Todo todo) {
        todoRepository.addTodo(user, todo);
    }

    @Override
    public List<Todo> getAll(User user) {
        return todoRepository.getTodos(user);
    }

    @Override
    public Optional<Todo> findById(User user, long id) {
        return todoRepository.findById(user, id);
    }

    @Override
    public void deleteById(User user, long id) {
        todoRepository.deleteById(user, id);
    }

    @Override
    public void updateTodo(User user, Todo todo) {
        todoRepository.updateTodo(user, todo);
    }

    @Override
    public long getNextId(User user) {
        return todoRepository.getNextTodoId(user);
    }
}
