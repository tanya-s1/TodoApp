package com.cg.todoapp.entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<Todo> todos;

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
        setTodos(new ArrayList<>());
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Todo> getTodos() { return todos; }
    public void setTodos(List<Todo> todos) { this.todos = todos; }
}

