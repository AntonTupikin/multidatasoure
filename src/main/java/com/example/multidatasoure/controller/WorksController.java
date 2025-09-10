package com.example.multidatasoure.controller;

import com.example.multidatasoure.controller.request.WorksCreateRequest;
import com.example.multidatasoure.controller.request.WorksPatchRequest;
import com.example.multidatasoure.controller.response.WorksResponse;
import com.example.multidatasoure.scenario.works.WorksCreateScenario;
import com.example.multidatasoure.scenario.works.WorksGetScenario;
import com.example.multidatasoure.scenario.works.WorksListByEmployeeScenario;
import com.example.multidatasoure.scenario.works.WorksPatchScenario;
import com.example.multidatasoure.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WorksController {
    private final WorksGetScenario worksGetScenario;
    private final WorksListByEmployeeScenario worksListByEmployeeScenario;
    private final WorksCreateScenario worksCreateScenario;
    private final WorksPatchScenario worksPatchScenario;
    private final UserService userService;

    @Operation(summary = "Получить работу по id", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/works/{id}")
    public WorksResponse getById(Principal principal, @PathVariable Long id) {
        return worksGetScenario.getById(userService.get(principal).getId(), id);
    }

    @Operation(summary = "Список работ сотрудника", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{employeeId}/works")
    public List<WorksResponse> getAllByEmployee(Principal principal, @PathVariable Long employeeId) {
        return worksListByEmployeeScenario.list(userService.get(principal).getId(), employeeId);
    }

    @Operation(summary = "Создать работу сотруднику", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{employeeId}/works")
    public WorksResponse create(Principal principal, @PathVariable Long employeeId, @RequestBody @Valid WorksCreateRequest request) {
        return worksCreateScenario.create(userService.get(principal).getId(), employeeId, request);
    }

    @Operation(summary = "Изменить работу", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/works/{id}")
    public WorksResponse patch(Principal principal, @PathVariable Long id, @RequestBody @Valid WorksPatchRequest request) {
        return worksPatchScenario.patch(userService.get(principal).getId(), id, request);
    }
}

