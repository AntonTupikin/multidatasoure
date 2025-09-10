package com.example.multidatasoure.controller;

import com.example.multidatasoure.controller.request.WorkLinePatchRequest;
import com.example.multidatasoure.controller.response.WorkLineResponse;
import com.example.multidatasoure.scenario.work.WorkLinePatchScenario;
import com.example.multidatasoure.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WorkLineController {
    private final WorkLinePatchScenario workLinePatchScenario;
    private final UserService userService;

    @Operation(summary = "Изменить строку работы", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/work-lines/{id}")
    public WorkLineResponse patch(Principal principal, @PathVariable Long id, @RequestBody @Valid WorkLinePatchRequest request) {
        return workLinePatchScenario.patch(userService.get(principal).getId(), id, request);
    }
}

