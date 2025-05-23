package com.cg.todoapp.service;

import com.cg.todoapp.entity.User;
import com.cg.todoapp.repository.UserRepository;

import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;
    private User currentUser;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            return false; // Username already taken
        }
        User newUser = new User(username, password);
        userRepository.save(newUser);
        return true;
    }

    public boolean login(String username, String password) {
        if (userRepository.authenticate(username, password)) {
            Optional<User> user = userRepository.findByUsername(username);
            user.ifPresent(value -> currentUser = value);
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
