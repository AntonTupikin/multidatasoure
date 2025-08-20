package com.example.multidatasoure.repository.primary;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.multidatasoure.entity.primary.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailIgnoreCase(String email);
    Optional<User> findByUsername(String username);
}
