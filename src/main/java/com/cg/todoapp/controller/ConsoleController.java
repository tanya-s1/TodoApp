package com.cg.todoapp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.cg.todoapp.entity.Priority;
import com.cg.todoapp.entity.Todo;
import com.cg.todoapp.service.TodoService;
import com.cg.todoapp.service.UserService;
import com.cg.todoapp.util.Validator;

public class ConsoleController {
    private final UserService userService;
    private final TodoService todoService;
    private final Scanner scanner;

    public ConsoleController(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
        this.scanner = new Scanner(System.in);
    }

    public void startApp() {
        while (true) {
            System.out.println("\n===== TODO APP =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    register();
                    break;
                case "2":
                    login();
                    break;
                case "3":
                    System.out.println("Exiting app. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userService.register(username, password)) {
            System.out.println("Registration successful.");
        } else {
            System.out.println("Username already exists.");
        }
    }

    private void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (userService.login(username, password)) {
            System.out.println("Login successful. Welcome " + username + "!");
            userMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private void userMenu() {
        while (userService.isLoggedIn()) {
            System.out.println("\n===== TODO MENU =====");
            System.out.println("1. Add Todo");
            System.out.println("2. View Todos");
            System.out.println("3. Mark Complete/Incomplete");
            System.out.println("4. Delete Todo");
            System.out.println("5. Search Todos");
            System.out.println("6. Filter Todos");
            System.out.println("7. Sort Todos");
            System.out.println("8. Logout");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1": addTodo(); break;
                case "2": viewTodos(); break;
                case "3": markComplete(); break;
                case "4": deleteTodo(); break;
                case "5": searchTodos(); break;
                case "6": filterTodos(); break;
                case "7": sortTodos(); break;
                case "8": userService.logout(); System.out.println("Logged out."); break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void addTodo() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        if (!Validator.isValidTitle(title)) {
            System.out.println("Invalid title.");
            return;
        }

        System.out.print("Description: ");
        String desc = scanner.nextLine();

        System.out.print("Priority (LOW/MEDIUM/HIGH): ");
        String priorityStr = scanner.nextLine();
        if (!Validator.isValidPriority(priorityStr)) {
            System.out.println("Invalid priority.");
            return;
        }

        System.out.print("Due Date (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();
        if (!Validator.isValidDate(dateStr)) {
            System.out.println("Invalid date format.");
            return;
        }

        Priority priority = Validator.parsePriority(priorityStr);
        LocalDate dueDate = Validator.parseDate(dateStr);
        todoService.addTodo(userService.getCurrentUser(), title, desc, priority, dueDate);
        System.out.println("Todo added!");
    }

    private void viewTodos() {
        List<Todo> todos = todoService.getTodos(userService.getCurrentUser());
        if (todos.isEmpty()) {
            System.out.println("No todos found.");
        } else {
            todos.forEach(this::printTodo);
        }
    }

    private void markComplete() {
        System.out.print("Enter Todo ID: ");
        long id = Long.parseLong(scanner.nextLine());
        System.out.print("Completed? (true/false): ");
        boolean completed = Boolean.parseBoolean(scanner.nextLine());
        if (todoService.markCompleted(userService.getCurrentUser(), id, completed)) {
            System.out.println("Todo updated.");
        } else {
            System.out.println("Todo not found.");
        }
    }

    private void deleteTodo() {
        System.out.print("Enter Todo ID to delete: ");
        long id = Long.parseLong(scanner.nextLine());
        if (todoService.deleteTodo(userService.getCurrentUser(), id)) {
            System.out.println("Todo deleted.");
        } else {
            System.out.println("Todo not found.");
        }
    }

    private void searchTodos() {
        System.out.print("Enter keyword to search: ");
        String keyword = scanner.nextLine();
        List<Todo> results = todoService.searchTodos(userService.getCurrentUser(), keyword);
        if (results.isEmpty()) {
            System.out.println("No todos matched.");
        } else {
            results.forEach(this::printTodo);
        }
    }

    private void filterTodos() {
        System.out.println("1. By Completion");
        System.out.println("2. By Priority");
        System.out.println("3. By Due Date Range");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                System.out.print("Completed? (true/false): ");
                boolean comp = Boolean.parseBoolean(scanner.nextLine());
                todoService.filterByCompletion(userService.getCurrentUser(), comp)
                           .forEach(this::printTodo);
                break;
            case "2":
                System.out.print("Priority (LOW/MEDIUM/HIGH): ");
                String pStr = scanner.nextLine();
                if (Validator.isValidPriority(pStr)) {
                    todoService.filterByPriority(userService.getCurrentUser(),
                            Validator.parsePriority(pStr)).forEach(this::printTodo);
                } else {
                    System.out.println("Invalid priority.");
                }
                break;
            case "3":
                System.out.print("From Date (yyyy-MM-dd): ");
                LocalDate from = Validator.parseDate(scanner.nextLine());
                System.out.print("To Date (yyyy-MM-dd): ");
                LocalDate to = Validator.parseDate(scanner.nextLine());
                todoService.filterByDueDate(userService.getCurrentUser(), from, to)
                           .forEach(this::printTodo);
                break;
            default:
                System.out.println("Invalid filter option.");
        }
    }

    private void sortTodos() {
        System.out.println("Sort by: createdAt / priority / dueDate");
        String field = scanner.nextLine();
        todoService.sortTodos(userService.getCurrentUser(), field)
                   .forEach(this::printTodo);
    }

    private void printTodo(Todo todo) {
        System.out.printf("ID: %d | %s | %s | Priority: %s | Due: %s | Done: %s%n",
                todo.getId(), todo.getTitle(), todo.getDescription(),
                todo.getPriority(), todo.getDueDate(), todo.isCompleted());
    }
}
