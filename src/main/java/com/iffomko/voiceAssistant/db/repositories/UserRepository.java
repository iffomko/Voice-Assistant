package com.iffomko.voiceAssistant.db.repositories;

import com.iffomko.voiceAssistant.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    boolean existsUserByUsername(String username);
}
