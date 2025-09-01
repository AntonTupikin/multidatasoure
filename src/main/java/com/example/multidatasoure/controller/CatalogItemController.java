package com.example.multidatasoure.controller;

import com.example.multidatasoure.controller.request.CatalogItemCreateRequest;
import com.example.multidatasoure.controller.response.CatalogItemResponse;
import com.example.multidatasoure.scenario.catalog.CatalogItemCreateScenario;
import com.example.multidatasoure.scenario.catalog.CatalogItemGetScenario;
import com.example.multidatasoure.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CatalogItemController {
    private final CatalogItemGetScenario catalogItemGetScenario;
    private final CatalogItemCreateScenario catalogItemCreateScenario;
    private final UserService userService;

    @Operation(summary = "Список глобальных позиций (каталог)", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/items")
    public Page<CatalogItemResponse> list(
            Principal principal,
            @ParameterObject Pageable pageable,
            @RequestParam(value = "q", required = false) String query
    ) {
        return catalogItemGetScenario.list(userService.get(principal).getId(), pageable, query);
    }

    @Operation(summary = "Создание позиции каталога", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.CREATED)
    @org.springframework.web.bind.annotation.PostMapping("/items")
    public CatalogItemResponse create(
            Principal principal,
            @jakarta.validation.Valid @org.springframework.web.bind.annotation.RequestBody CatalogItemCreateRequest request
    ) {
        return catalogItemCreateScenario.create(userService.get(principal).getId(), request);
    }
}
