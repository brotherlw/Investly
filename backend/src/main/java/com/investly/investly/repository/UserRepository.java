package com.investly.investly.repository;

import com.investly.investly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findUserByUsername(String username);
    boolean existsByUsername(String username);
}