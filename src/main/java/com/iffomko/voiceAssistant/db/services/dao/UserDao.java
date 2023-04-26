package com.iffomko.voiceAssistant.db.services.dao;

import com.iffomko.voiceAssistant.db.entities.Role;
import com.iffomko.voiceAssistant.db.entities.User;
import com.iffomko.voiceAssistant.db.repositories.UserRepository;
import com.iffomko.voiceAssistant.db.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserDao implements UserService {
    private final UserRepository repository;

    @Autowired
    public UserDao(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addUser(User user) {
        repository.save(user);
    }

    @Override
    public boolean deleteUserById(int id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);

        return true;
    }

    @Override
    public User getUserById(int id) {
        Optional<User> optionalUser = repository.findById(id);

        if (optionalUser.isEmpty()) {
            return null;
        }

        return optionalUser.get();
    }

    @Override
    public List<Role> getUserRolesById(int id) {
        Optional<User> optionalUser = repository.findById(id);

        if (optionalUser.isEmpty()) {
            return new ArrayList<>();
        }

        return optionalUser.get().getRoles();
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public void addUserRoleById(int id, Role role) {
        Optional<User> optionalUser = repository.findById(id);

        if (optionalUser.isEmpty()) {
            return;
        }

        optionalUser.get().getRoles().add(role);
    }
}
