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

public class TodoService {
    // private final todoRepository todoRepository;
        private final TodoDao todoDao;


    public TodoService(TodoDao todoDao) {
        this.todoDao = todoDao;
    }

    public void addTodo(User user, String title, String desc, Priority priority, LocalDateTime dueDateTime) {
        long id = todoDao.getNextId(user);
        Todo todo = new Todo(id, title, desc, priority, dueDateTime);
        todoDao.addTodo(user, todo);
    }

    public List<Todo> getAll(User user) {
        return todoDao.getAll(user);
    }

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

    public boolean deleteTodo(User user, long id) {
        Optional<Todo> todoOpt = todoDao.findById(user, id);
        if (todoOpt.isPresent()) {
            todoDao.deleteById(user, id);
            return true;
        }
        return false;
    }

    public List<Todo> searchTodos(User user, String keyword) {
        return todoDao.getAll(user).stream()
            .filter(todo -> todo.getTitle().toLowerCase().contains(keyword.toLowerCase())
                         || todo.getDescription().toLowerCase().contains(keyword.toLowerCase()))
            .collect(Collectors.toList());
    }

    public List<Todo> filterByCompletion(User user, boolean completed) {
        return todoDao.getAll(user).stream()
                .filter(todo -> todo.isCompleted() == completed)
                .collect(Collectors.toList());
    }

    public List<Todo> filterByPriority(User user, Priority priority) {
        return todoDao.getAll(user).stream()
                .filter(todo -> todo.getPriority() == priority)
                .collect(Collectors.toList());
    }

    public List<Todo> filterByDueDate(User user, LocalDateTime from, LocalDateTime to) {
    return todoDao.getAll(user).stream()
            .filter(todo -> (todo.getDueDateTime().isEqual(from) || todo.getDueDateTime().isAfter(from)) &&
                            (todo.getDueDateTime().isEqual(to) || todo.getDueDateTime().isBefore(to)))
            .collect(Collectors.toList());
}


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
