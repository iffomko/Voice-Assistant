package com.iffomko.voiceAssistant.db.services;

import com.iffomko.voiceAssistant.db.entities.Role;
import com.iffomko.voiceAssistant.db.entities.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    boolean deleteUserById(int id);
    User getUserById(int id);
    List<Role> getUserRolesById(int id);
    List<User> getAllUsers();
    void addUserRoleById(int id, Role role);
}
