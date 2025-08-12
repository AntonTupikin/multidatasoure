package com.example.multidatasoure.controller;

import com.example.multidatasoure.dto.ForgotPasswordRequest;
import com.example.multidatasoure.dto.LoginRequest;
import com.example.multidatasoure.dto.UserDto;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request.getUsername(), request.getPassword()));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        userService.forgotPassword(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody UserDto dto) {
        return userService.editUser(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
