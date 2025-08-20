package com.example.multidatasoure.controller;

import com.example.multidatasoure.controller.request.AuthenticationRequest;
import com.example.multidatasoure.controller.request.UserCreateRequest;
import com.example.multidatasoure.controller.response.AuthenticationResponse;
import com.example.multidatasoure.controller.response.UserResponse;
import com.example.multidatasoure.scenario.user.UserCreateScenario;
import com.example.multidatasoure.security.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
    private final UserCreateScenario userCreateScenario;
    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Регистрация")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public UserResponse register(@RequestBody @Valid UserCreateRequest request) {
        return userCreateScenario.create(request);
    }

    @Operation(
            summary = "Логин")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody AuthenticationRequest request) {
        return authenticationService.login(request);
    }
}
