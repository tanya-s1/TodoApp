package com.cg.todoapp.service;

import java.util.Optional;

import com.cg.todoapp.dao.UserDao;
import com.cg.todoapp.entity.User;

public class UserService {
    // private final userRepository userRepository;
    private final UserDao userDao;
    private User currentUser;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean register(String username, String password) {
        if (userDao.exists(username)) {
            return false; // Username already taken
        }
        User newUser = new User(username, password);
        userDao.save(newUser);
        return true;
    }

    public boolean login(String username, String password) {
        if (userDao.authenticate(username, password)) {
            Optional<User> user = userDao.findByUsername(username);
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
