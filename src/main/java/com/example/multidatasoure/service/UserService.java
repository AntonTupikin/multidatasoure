package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.request.UserCreateRequest;
import com.example.multidatasoure.entity.primary.Role;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.exception.ConflictException;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.repository.primary.UserRepository;
import com.example.multidatasoure.utils.FieldsUtils;
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

    private void checkEmail(String email) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new ConflictException("message.exception.conflict.user", email);
        }
    }

    public void patchPassword(User user, String password) {
        user.setPassword(password);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
    }

    public User get(Principal principal) {
        return getByUsername(principal.getName());
    }

    public User save(UserCreateRequest request, Role role) {
        User user = new User();
        String email = FieldsUtils.normalizeEmail(request.email());
        checkEmail(email);
        user.setUsername(email);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(role);
        return userRepository.save(user);
    }

    public User getByUsername(String username) {
        return findByUsername(username).orElseThrow(() -> new NotFoundException("message.exception.not-found.user"));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
