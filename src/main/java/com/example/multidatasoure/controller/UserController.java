package com.example.multidatasoure.controller;

import com.example.multidatasoure.controller.request.UserPatchRequest;
import com.example.multidatasoure.controller.response.UserResponse;
import com.example.multidatasoure.scenario.user.UserDeleteScenario;
import com.example.multidatasoure.scenario.user.UserGetScenario;
import com.example.multidatasoure.scenario.user.UserPatchScenario;
import com.example.multidatasoure.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}

