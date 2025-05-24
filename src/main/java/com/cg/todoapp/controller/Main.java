package com.cg.todoapp.controller;

import com.cg.todoapp.dao.TodoDao;
import com.cg.todoapp.dao.TodoDaoImpl;
import com.cg.todoapp.dao.UserDao;
import com.cg.todoapp.dao.UserDaoImpl;
import com.cg.todoapp.service.TodoService;
import com.cg.todoapp.service.UserService;

public class Main {
    public static void main(String[] args) {

        /*
         * Previously, repositories were used for persistence:
         * UserRepository userRepo = new UserRepository();
         * TodoRepository todoRepo = new TodoRepository();
         * 
         * DAOs were instantiated by passing repositories:
         * UserDao userDao = new UserDaoImpl(userRepo);
         * TodoDao todoDao = new TodoDaoImpl(todoRepo);
         */

        /*
         * Now, DAOs are created by specifying JSON file names for data storage:
         * This suggests the DAOs will handle reading and writing user and todo data
         * to and from the respective JSON files: users.json and user_todos.json
         */
        UserDao userDao = new UserDaoImpl("users.json");
        TodoDao todoDao = new TodoDaoImpl("user_todos.json");

        /*
         * Create service layer objects by passing DAO implementations.
         * The service layer abstracts the data access logic and business logic
         * for users and todos respectively.
         */
        UserService userService = new UserService(userDao);
        TodoService todoService = new TodoService(todoDao);

        /*
         * Create the console controller by injecting the service layer instances.
         * The ConsoleController handles user interaction and controls app flow.
         * 
         * Start the console-based Todo app by calling startApp() method.
         */
        ConsoleController controller = new ConsoleController(userService, todoService);
        controller.startApp();
    }
}
