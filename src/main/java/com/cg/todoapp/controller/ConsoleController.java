package com.cg.todoapp.controller;

import java.time.LocalDateTime;
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

    // Constructor to initialize services and input scanner
    public ConsoleController(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
        this.scanner = new Scanner(System.in);
    }

    // Entry point to run the Todo application
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
                    register(); // User registration
                    break;
                case "2":
                    login(); // User login
                    break;
                case "3":
                    System.out.println("Exiting app. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // Handles user registration
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

    // Handles user login
    private void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (userService.login(username, password)) {
            System.out.println("Login successful. Welcome " + username + "!");
            userMenu(); // Redirect to user menu on successful login
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    // Menu for logged-in users to manage todos
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
                case "8":
                    userService.logout(); // Logs out user
                    System.out.println("Logged out.");
                    break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    // Handles creation of a new todo with validation
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

        System.out.print("Due Date & Time (yyyy-MM-dd HH:mm): ");
        String dateStr = scanner.nextLine();
        if (!Validator.isValidDateTime(dateStr)) {
            System.out.println("Invalid date-time format.");
            return;
        }

        LocalDateTime dueDateTime = Validator.parseDateTime(dateStr);
        Priority priority = Validator.parsePriority(priorityStr);

        todoService.addTodo(userService.getCurrentUser(), title, desc, priority, dueDateTime);
        System.out.println("Todo added!");
    }

    // Displays all todos for the current user
    private void viewTodos() {
        List<Todo> todos = todoService.getAll(userService.getCurrentUser());
        if (todos.isEmpty()) {
            System.out.println("No todos found.");
        } else {
            todos.forEach(this::printTodo);
        }
    }

    // Allows user to mark a todo as complete or incomplete
    private void markComplete() {
        viewTodos();
        System.out.println();
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

    // Deletes a todo by ID
    private void deleteTodo() {
        viewTodos();
        System.out.print("Enter Todo ID to delete: ");
        long id = Long.parseLong(scanner.nextLine());
        if (todoService.deleteTodo(userService.getCurrentUser(), id)) {
            System.out.println("Todo deleted.");
        } else {
            System.out.println("Todo not found.");
        }
    }

    // Searches todos by keyword in title or description
    private void searchTodos() {
        System.out.print("Enter keyword to search: ");
        System.out.println();
        String keyword = scanner.nextLine();
        List<Todo> results = todoService.searchTodos(userService.getCurrentUser(), keyword);
        if (results.isEmpty()) {
            System.out.println("No todos matched.");
        } else {
            results.forEach(this::printTodo);
        }
    }

    // Filters todos based on completion status, priority, or due date range
    private void filterTodos() {
        System.out.println("1. By Completion");
        System.out.println("2. By Priority");
        System.out.println("3. By Due Date-Time Range");
        System.out.print("Choose filter option: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.print("Completed? (true/false): ");
                String boolInput = scanner.nextLine().trim().toLowerCase();
                if (boolInput.equals("true") || boolInput.equals("false")) {
                    boolean completed = Boolean.parseBoolean(boolInput);
                    todoService.filterByCompletion(userService.getCurrentUser(), completed)
                               .forEach(this::printTodo);
                } else {
                    System.out.println("Invalid input. Please enter true or false.");
                }
                break;

            case "2":
                System.out.print("Priority (LOW/MEDIUM/HIGH): ");
                String pStr = scanner.nextLine().trim().toUpperCase();
                if (Validator.isValidPriority(pStr)) {
                    todoService.filterByPriority(
                            userService.getCurrentUser(),
                            Validator.parsePriority(pStr)
                    ).forEach(this::printTodo);
                } else {
                    System.out.println("Invalid priority. Please enter LOW, MEDIUM, or HIGH.");
                }
                break;

            case "3":
                System.out.print("From Date & Time (yyyy-MM-dd HH:mm): ");
                String fromStr = scanner.nextLine();
                if (!Validator.isValidDateTime(fromStr)) {
                    System.out.println("Invalid format.");
                    return;
                }
                LocalDateTime from = Validator.parseDateTime(fromStr);

                System.out.print("To Date & Time (yyyy-MM-dd HH:mm): ");
                String toStr = scanner.nextLine();
                if (!Validator.isValidDateTime(toStr)) {
                    System.out.println("Invalid format.");
                    return;
                }
                LocalDateTime to = Validator.parseDateTime(toStr);

                todoService.filterByDueDate(userService.getCurrentUser(), from, to)
                           .forEach(this::printTodo);
                break;

            default:
                System.out.println("Invalid filter option. Please choose 1, 2, or 3.");
        }
    }

    // Sorts todos based on selected field
    private void sortTodos() {
        System.out.println("Sort by: createdAt / priority / dueDate");
        String field = scanner.nextLine();
        todoService.sortTodos(userService.getCurrentUser(), field).forEach(this::printTodo);
    }

    // Utility method to print a single todo in formatted form
    private void printTodo(Todo todo) {
        String due = todo.getDueDateTime().format(Validator.getFormatter());
        String created = todo.getCreatedAt().format(Validator.getFormatter());

        System.out.printf("ID: %d | %s | %s | Priority: %s | Due: %s | Created: %s | Done: %s%n",
                todo.getId(), todo.getTitle(), todo.getDescription(),
                todo.getPriority(), due, created, todo.isCompleted());
    }
}
