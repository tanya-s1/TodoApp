package com.cg.todoapp.dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cg.todoapp.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UserDaoImpl implements UserDao {

    private static final String FILE_NAME = "users.json";
    private final Gson gson = new Gson();

    private List<User> loadUsers() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();

        try (Reader reader = new FileReader(file)) {
            Type userListType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(reader, userListType);
            return users != null ? users : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveUsers(List<User> users) {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean exists(String username) {
        return loadUsers().stream()
                .anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    @Override
    public void save(User user) {
        List<User> users = loadUsers();

        // If exists, update password (or skip if you want strict immutability)
        Optional<User> existing = users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(user.getUsername()))
                .findFirst();

        existing.ifPresent(users::remove);
        users.add(user);
        saveUsers(users);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return loadUsers().stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    @Override
    public boolean authenticate(String username, String password) {
        return loadUsers().stream()
                .anyMatch(user -> user.getUsername().equalsIgnoreCase(username)
                        && user.getPassword().equals(password));
    }
}
