package com.cg.todoapp.service;

import com.cg.todoapp.entity.Priority;
import com.cg.todoapp.entity.Todo;
import com.cg.todoapp.entity.User;
import com.cg.todoapp.repository.TodoRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void addTodo(User user, String title, String desc, Priority priority, LocalDate dueDate) {
        long id = todoRepository.getNextTodoId(user);
        Todo todo = new Todo(id, title, desc, priority, dueDate);
        todoRepository.addTodo(user, todo);
        
    }

    public List<Todo> getTodos(User user) {
        return todoRepository.getTodos(user);
    }

    public boolean markCompleted(User user, long id, boolean completed) {
        Optional<Todo> todoOpt = todoRepository.findById(user, id);
        if (todoOpt.isPresent()) {
            Todo todo = todoOpt.get();
            todo.setCompleted(completed);
            todoRepository.updateTodo(user, todo);
            return true;
        }
        return false;
    }

    public boolean deleteTodo(User user, long id) {
        Optional<Todo> todoOpt = todoRepository.findById(user, id);
        if (todoOpt.isPresent()) {
            todoRepository.deleteById(user, id);
            return true;
        }
        return false;
    }

    public List<Todo> searchTodos(User user, String keyword) {
        return todoRepository.getTodos(user).stream()
            .filter(todo -> todo.getTitle().toLowerCase().contains(keyword.toLowerCase())
                         || todo.getDescription().toLowerCase().contains(keyword.toLowerCase()))
            .collect(Collectors.toList());
    }

    public List<Todo> filterByCompletion(User user, boolean completed) {
        return todoRepository.getTodos(user).stream()
                .filter(todo -> todo.isCompleted() == completed)
                .collect(Collectors.toList());
    }

    public List<Todo> filterByPriority(User user, Priority priority) {
        return todoRepository.getTodos(user).stream()
                .filter(todo -> todo.getPriority() == priority)
                .collect(Collectors.toList());
    }

    public List<Todo> filterByDueDate(User user, LocalDate from, LocalDate to) {
        return todoRepository.getTodos(user).stream()
                .filter(todo -> (todo.getDueDate().isEqual(from) || todo.getDueDate().isAfter(from)) &&
                                (todo.getDueDate().isEqual(to) || todo.getDueDate().isBefore(to)))
                .collect(Collectors.toList());
    }

    public List<Todo> sortTodos(User user, String sortBy) {
        Comparator<Todo> comparator;

        switch (sortBy.toLowerCase()) {
            case "priority":
                comparator = Comparator.comparing(Todo::getPriority);
                break;
            case "duedate":
                comparator = Comparator.comparing(Todo::getDueDate);
                break;
            case "createdat":
            default:
                comparator = Comparator.comparing(Todo::getCreatedAt);
        }

        return todoRepository.getTodos(user).stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
