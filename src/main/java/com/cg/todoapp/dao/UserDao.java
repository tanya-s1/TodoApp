package com.cg.todoapp.dao;

import java.util.Optional;

import com.cg.todoapp.entity.User;

public interface UserDao {
    boolean exists(String username);
    void save(User user);
    Optional<User> findByUsername(String username);
    boolean authenticate(String username, String password);
}
