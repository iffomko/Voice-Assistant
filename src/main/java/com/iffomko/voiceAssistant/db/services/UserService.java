package com.iffomko.voiceAssistant.db.services;

import com.iffomko.voiceAssistant.db.entities.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    boolean deleteUserById(int id);
    User getUserById(int id);
    User findUserByUsername(String username);
    List<User> getAllUsers();
}
