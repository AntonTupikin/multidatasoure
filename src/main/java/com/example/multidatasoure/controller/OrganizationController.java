package com.example.multidatasoure.controller;

import com.example.multidatasoure.controller.request.OrganizationCreateRequest;
import com.example.multidatasoure.controller.request.OrganizationPatchRequest;
import com.example.multidatasoure.controller.response.OrganizationResponse;
import com.example.multidatasoure.scenario.organization.OrganizationCreateScenario;
import com.example.multidatasoure.scenario.organization.OrganizationDeleteScenario;
import com.example.multidatasoure.scenario.organization.OrganizationGetScenario;
import com.example.multidatasoure.scenario.organization.OrganizationPatchScenario;
import com.example.multidatasoure.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OrganizationController {
    private final OrganizationPatchScenario organizationPatchScenario;
    private final OrganizationCreateScenario organizationCreateScenario;
    private final OrganizationDeleteScenario organizationDeleteScenario;
    private final UserService userService;
    private final OrganizationGetScenario organizationGetScenario;

    @Operation(
            summary = "Получение всех организаций пользователя",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/organization")
    public List<OrganizationResponse> getAll(Principal principal) {
        return organizationGetScenario.getAllByUser(userService.get(principal).getId());
    }

    @Operation(
            summary = "Получение информации об организации по ее id",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/organization/{id}")
    public OrganizationResponse getById(@PathVariable Long id, Principal principal) {
        return organizationGetScenario.findByIdAndUser(id, userService.get(principal).getId());
    }

    @Operation(
            summary = "Создание организации",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/organization")
    public OrganizationResponse create(Principal principal, @RequestBody @Valid OrganizationCreateRequest organizationCreateRequest) {
        return organizationCreateScenario.create(organizationCreateRequest, userService.get(principal).getId());
    }

    @Operation(
            summary = "Изменение организации",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/organization/{id}")
    public OrganizationResponse patch(@PathVariable Long id, Principal principal, @RequestBody @Valid OrganizationPatchRequest organizationPatchRequest) {
        return organizationPatchScenario.patch(id, organizationPatchRequest, userService.get(principal).getId());
    }

    @Operation(
            summary = "Удаление организации",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/organization/{id}")
    public void delete(@PathVariable Long id, Principal principal) {
        organizationDeleteScenario.delete(id, userService.get(principal).getId());
    }
}
