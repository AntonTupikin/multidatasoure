package com.example.multidatasoure.controller;

import com.example.multidatasoure.controller.request.OrganizationCreateRequest;
import com.example.multidatasoure.controller.response.OrganizationResponse;
import com.example.multidatasoure.scenario.OrganizationCreateScenario;
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
public class OrganizationController {

    private final OrganizationCreateScenario organizationCreateScenario;
    private final UserService userService;

    @Operation(
            summary = "Создание организации",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/organization")
    public OrganizationResponse create(Principal principal, @RequestBody @Valid OrganizationCreateRequest organizationCreateRequest) {
        return organizationCreateScenario.create(organizationCreateRequest, userService.get(principal).getId());
    }
}
