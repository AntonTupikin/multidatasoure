package com.example.multidatasoure.controller;

import com.example.multidatasoure.controller.request.BusinessPartnerCreateRequest;
import com.example.multidatasoure.controller.response.BusinessPartnerResponse;
import com.example.multidatasoure.scenario.partner.BusinessPartnerCreateScenario;
import com.example.multidatasoure.scenario.partner.BusinessPartnerDeleteScenario;
import com.example.multidatasoure.scenario.partner.BusinessPartnerListScenario;
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
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class BusinessPartnerController {
    private final BusinessPartnerCreateScenario createScenario;
    private final BusinessPartnerListScenario listScenario;
    private final BusinessPartnerDeleteScenario deleteScenario;
    private final UserService userService;

    @Operation(summary = "Создать контрагента", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BusinessPartnerResponse create(Principal principal, @RequestBody @Valid BusinessPartnerCreateRequest request) {
        return createScenario.create(userService.get(principal).getId(), request);
    }

    @Operation(summary = "Список контрагентов пользователя", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<BusinessPartnerResponse> list(Principal principal) {
        return listScenario.list(userService.get(principal).getId());
    }

    @Operation(summary = "Удалить контрагента", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(Principal principal, @PathVariable Long id) {
        deleteScenario.delete(userService.get(principal).getId(), id);
    }
}

