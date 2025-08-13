package com.example.multidatasoure.service;

import com.example.multidatasoure.dto.UserDto;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.repository.primary.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

/**
 * Provides user related operations such as registration,
 * authentication and profile updates.
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
    }

    public User get(Principal principal) {
        return getByUsername(principal.getName());
    }

    public User register(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }
        return userRepository.save(user);
    }

    public User getByUsername(String username) {
        return findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> patchUser(Long id, UserDto dto) {
        return userRepository.findById(id).map(user -> {
            if (dto.getEmail() != null) {
                user.setEmail(dto.getEmail());
            }
            if (dto.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
            }
            if (dto.getRole() != null) {
                user.setRole(dto.getRole());
            }
            return userRepository.save(user);
        });
    }

    public void forgotPassword(String email) {
        // Placeholder for password recovery logic
    }
}
