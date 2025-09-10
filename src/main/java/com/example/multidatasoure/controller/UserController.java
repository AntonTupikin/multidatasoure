package com.example.multidatasoure.controller;

import com.example.multidatasoure.controller.request.UserCreateRequest;
import com.example.multidatasoure.controller.request.UserFilter;
import com.example.multidatasoure.controller.request.UserPatchRequest;
import com.example.multidatasoure.controller.response.UserResponse;
import com.example.multidatasoure.scenario.user.UserCreateScenario;
import com.example.multidatasoure.scenario.user.UserDeleteScenario;
import com.example.multidatasoure.scenario.user.UserGetScenario;
import com.example.multidatasoure.scenario.user.UserPatchScenario;
import com.example.multidatasoure.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class UserController {
    private final UserGetScenario userGetScenario;
    private final UserService userService;
    private final UserPatchScenario userPatchScenario;
    private final UserDeleteScenario userDeleteScenario;
    private final UserCreateScenario userCreateScenario;

    @Operation(
            summary = "Получение информации о пользователе",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public Page<UserResponse> getAll(Principal principal, @ParameterObject Pageable pageable, @ParameterObject UserFilter userFilter) {
        return userGetScenario.getAllByUser(userService.get(principal).getId(), pageable, userFilter);
    }

    @Operation(
            summary = "Получение пользователя по id",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{id}")
    public UserResponse getInfo(@PathVariable Long id, Principal principal) {
        return userGetScenario.getById(id, userService.get(principal).getId());
    }

    @Operation(
            summary = "Получение информации о пользователе",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/me")
    public UserResponse getInfo(Principal principal) {
        return userGetScenario.get(userService.get(principal).getId());
    }

    @Operation(
            summary = "Изменение пользователя",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/users/{id}")
    public UserResponse patch(@PathVariable Long id, @RequestBody UserPatchRequest request) {
        return userPatchScenario.patch(id, request);
    }

    @Operation(
            summary = "Удаление пользователя",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/users")
    public void delete(Principal principal) {
        userDeleteScenario.delete(userService.get(principal).getId());
    }

    @Operation(
            summary = "Создание работника пользователя",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/users/employees")
    public UserResponse createEmployee(@RequestBody UserCreateRequest request, Principal principal) {
        return userCreateScenario.createEmployee(request, userService.get(principal).getId());
    }

}

