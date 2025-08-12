package com.example.multidatasoure.service;

import com.example.multidatasoure.dto.UserDto;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.repository.primary.UserRepository;
import com.example.multidatasoure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
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
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final LogEntriesService logEntriesService;

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

    public String login(String username, String password) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        logEntriesService.save("Log into lk user %s %s".formatted(username, OffsetDateTime.now()));
        return jwtTokenProvider.createToken(auth);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> editUser(Long id, UserDto dto) {
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
