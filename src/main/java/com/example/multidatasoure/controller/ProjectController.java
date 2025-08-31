package com.example.multidatasoure.controller;

import com.example.multidatasoure.controller.request.ProjectCreateRequest;
import com.example.multidatasoure.controller.request.ProjectFilter;
import com.example.multidatasoure.controller.response.ProjectResponse;
import com.example.multidatasoure.controller.response.UserResponse;
import com.example.multidatasoure.scenario.project.ProjectCreateScenario;
import com.example.multidatasoure.scenario.project.ProjectDeleteScenario;
import com.example.multidatasoure.scenario.project.ProjectGetScenario;
import com.example.multidatasoure.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
public class ProjectController {
    private final ProjectCreateScenario projectCreateScenario;
    private final ProjectGetScenario projectGetScenario;
    private final UserService userService;
    private final ProjectDeleteScenario projectDeleteScenario;

    @Operation(
            summary = "Получение всех проектов организации",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/projects")
    public Page<ProjectResponse> getAll(Principal principal, @ParameterObject Pageable pageable, @ParameterObject ProjectFilter projectFilter) {
        return projectGetScenario.getAll(userService.get(principal).getId(), pageable, projectFilter);
    }

    @Operation(
            summary = "Получение информации о проекте",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/projects/{id}")
    public UserResponse getInfo(Principal principal, @PathVariable Long id) {
        return projectGetScenario.get(userService.get(principal).getId(), id);
    }

    @Operation(
            summary = "Создание проекта",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/projects")
    public ProjectResponse create(Principal principal, @RequestBody @Valid ProjectCreateRequest request) {
        return projectCreateScenario.create(userService.get(principal).getId(), request);
    }

    @Operation(
            summary = "Удаление проекта",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/projects/{id}")
    public void delete(Principal principal, @PathVariable Long id) {
        projectDeleteScenario.delete(userService.get(principal).getId(), id);
    }
}
