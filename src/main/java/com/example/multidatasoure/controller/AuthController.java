package com.example.multidatasoure.controller;

import com.example.multidatasoure.controller.request.ForgotPasswordRequest;
import com.example.multidatasoure.controller.request.LoginRequest;
import com.example.multidatasoure.controller.response.LoginResponse;
import com.example.multidatasoure.dto.UserDto;
import com.example.multidatasoure.controller.response.UserResponse;
import com.example.multidatasoure.mapper.UserMapper;
import com.example.multidatasoure.security.AuthenticationService;
import com.example.multidatasoure.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(
            summary = "Регистрация")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userMapper.toUserResponse(userService.register(dto)));
    }

    @Operation(
            summary = "Логин")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authenticationService.login(request);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        userService.forgotPassword(request.getEmail());
        return ResponseEntity.ok().build();
    }

}
