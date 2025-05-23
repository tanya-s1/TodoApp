package com.cg.todoapp.controller;

import com.cg.todoapp.repository.TodoRepository;
import com.cg.todoapp.repository.UserRepository;
import com.cg.todoapp.service.TodoService;
import com.cg.todoapp.service.UserService;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepo = new UserRepository();
        TodoRepository todoRepo = new TodoRepository();
        UserService userService = new UserService(userRepo);
        TodoService todoService = new TodoService(todoRepo);
        ConsoleController controller = new ConsoleController(userService, todoService);
        controller.startApp();
    }
}

