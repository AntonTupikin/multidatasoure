package com.example.multidatasoure.controller;

import com.example.multidatasoure.controller.request.UserCreateRequest;
import com.example.multidatasoure.controller.response.UserResponse;
import com.example.multidatasoure.scenario.employee.EmployeeCreateScenario;
import com.example.multidatasoure.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final UserService userService;
    private final EmployeeCreateScenario employeeCreateScenario;

    @Operation(
            summary = "Создание работника",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/employees")
    public UserResponse create(@RequestBody @Valid UserCreateRequest request, Principal principal) {
        return employeeCreateScenario.create(request, userService.get(principal).getId());
    }
}
