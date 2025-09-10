package com.example.multidatasoure.controller;

import com.example.multidatasoure.controller.request.WorkCreateRequest;
import com.example.multidatasoure.controller.request.WorkPatchRequest;
import com.example.multidatasoure.controller.response.WorkResponse;
import com.example.multidatasoure.scenario.work.WorkCreateScenario;
import com.example.multidatasoure.scenario.work.WorksGetScenario;
import com.example.multidatasoure.scenario.work.WorksListByEmployeeScenario;
import com.example.multidatasoure.scenario.work.WorksPatchScenario;
import com.example.multidatasoure.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WorkController {
    private final WorksGetScenario worksGetScenario;
    private final WorksListByEmployeeScenario worksListByEmployeeScenario;
    private final WorkCreateScenario workCreateScenario;
    private final WorksPatchScenario worksPatchScenario;
    private final UserService userService;

    @Operation(summary = "Получить работу по id", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/works/{id}")
    public WorkResponse getById(Principal principal, @PathVariable Long id) {
        return worksGetScenario.getById(userService.get(principal).getId(), id);
    }

    @Operation(summary = "Список работ сотрудника", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{employeeId}/works")
    public List<WorkResponse> getAllByEmployee(Principal principal, @PathVariable Long employeeId) {
        return worksListByEmployeeScenario.list(userService.get(principal).getId(), employeeId);
    }

    @Operation(summary = "Создать работу сотруднику", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{employeeId}/works")
    public WorkResponse create(Principal principal, @PathVariable Long employeeId, @RequestBody @Valid WorkCreateRequest request) {
        return workCreateScenario.create(userService.get(principal).getId(), employeeId, request);
    }

    @Operation(summary = "Изменить работу", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/works/{id}")
    public WorkResponse patch(Principal principal, @PathVariable Long id, @RequestBody @Valid WorkPatchRequest request) {
        return worksPatchScenario.patch(userService.get(principal).getId(), id, request);
    }
}

