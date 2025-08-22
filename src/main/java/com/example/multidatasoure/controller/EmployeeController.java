package com.example.multidatasoure.controller;

import com.example.multidatasoure.controller.request.EmployeeFilter;
import com.example.multidatasoure.controller.request.EmployeePatchRequest;
import com.example.multidatasoure.controller.request.UserCreateRequest;
import com.example.multidatasoure.controller.response.UserResponse;
import com.example.multidatasoure.scenario.employee.EmployeeCreateScenario;
import com.example.multidatasoure.scenario.employee.EmployeeGetScenario;
import com.example.multidatasoure.scenario.employee.EmployeePatchScenario;
import com.example.multidatasoure.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final EmployeeGetScenario employeeGetScenario;
    private final EmployeePatchScenario employeePatchScenario;

    @Operation(
            summary = "Создание работника",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/employees")
    public UserResponse create(@RequestBody @Valid UserCreateRequest request, Principal principal) {
        return employeeCreateScenario.create(request, userService.get(principal).getId());
    }

    @Operation(
            summary = "Получение работника по id",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/employees/{id}")
    public UserResponse getById(@PathVariable Long id, Principal principal) {
        return employeeGetScenario.findByIdAndUser(id, userService.get(principal).getId());
    }

    @Operation(
            summary = "Получение всех работников пользователя",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/employees/getAll")
    public Page<UserResponse> getAll(@ParameterObject Pageable pageable, Principal principal, @ParameterObject EmployeeFilter employeeFilter) {
        return employeeGetScenario.getAllByUser(userService.get(principal).getId(), pageable, employeeFilter);
    }

    @Operation(
            summary = "Редактирование работника",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/employees/{id}")
    public UserResponse patch(@PathVariable Long id, @RequestBody EmployeePatchRequest request, Principal principal) {
        return employeePatchScenario.patch(id, request, userService.get(principal).getId());
    }
}
