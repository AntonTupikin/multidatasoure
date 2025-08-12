package com.example.multidatasoure.service;

import com.example.multidatasoure.dto.UserDto;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.repository.primary.UserRepository;
import com.example.multidatasoure.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Provides user related operations such as registration,
 * authentication and profile updates.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
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

    public String login(String username, String password) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
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
