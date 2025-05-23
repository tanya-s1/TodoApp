package com.cg.todoapp.repository;

import com.cg.todoapp.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository {
    private Map<String, User> users = new HashMap<>();

    public boolean existsByUsername(String username) {
        return users.containsKey(username);
    }

    public void save(User user) {
        users.put(user.getUsername(), user);
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).getPassword().equals(password);
    }

    public Map<String, User> getAllUsers() {
        return users;
    }
}
