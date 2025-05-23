package com.cg.todoapp.controller;

import com.cg.todoapp.dao.TodoDao;
import com.cg.todoapp.dao.TodoDaoImpl;
import com.cg.todoapp.dao.UserDao;
import com.cg.todoapp.dao.UserDaoImpl;
import com.cg.todoapp.service.TodoService;
import com.cg.todoapp.service.UserService;

public class Main {
    public static void main(String[] args) {


        // UserRepository userRepo = new UserRepository();
        // TodoRepository todoRepo = new TodoRepository();

        // UserDao userDao = new UserDaoImpl(userRepo);
        // TodoDao todoDao = new TodoDaoImpl(todoRepo);
        
        UserDao userDao = new UserDaoImpl();
        TodoDao todoDao = new TodoDaoImpl();


        // Create service layer with DAOs
        UserService userService = new UserService(userDao);
        TodoService todoService = new TodoService(todoDao);

        // Create controller and start the console-based app
        ConsoleController controller = new ConsoleController(userService, todoService);
        controller.startApp();
    }
}
