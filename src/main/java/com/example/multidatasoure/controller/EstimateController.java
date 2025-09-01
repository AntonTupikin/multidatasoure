package com.example.multidatasoure.controller;

import com.example.multidatasoure.controller.request.EstimateCreateRequest;
import com.example.multidatasoure.controller.request.EstimateItemCreateRequest;
import com.example.multidatasoure.controller.response.EstimateItemResponse;
import com.example.multidatasoure.controller.response.EstimateResponse;
import com.example.multidatasoure.scenario.estimate.*;
import com.example.multidatasoure.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * REST API для работы со сметами и их позициями.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class EstimateController {
    private final EstimateCreateScenario estimateCreateScenario;
    private final EstimateGetScenario estimateGetScenario;
    private final EstimateDeleteScenario estimateDeleteScenario;
    private final EstimateItemAddScenario estimateItemAddScenario;
    private final EstimateItemDeleteScenario estimateItemDeleteScenario;
    private final EstimateItemPatchScenario estimateItemPatchScenario;
    private final EstimateItemsUpsertScenario estimateItemsUpsertScenario;
    private final EstimateItemGetScenario estimateItemGetScenario;
    private final UserService userService;
    private final EstimateItemHistoryScenario estimateItemHistoryScenario;
    private final com.example.multidatasoure.scenario.estimate.EstimateCatalogItemAddScenario estimateCatalogItemAddScenario;
    private final com.example.multidatasoure.scenario.estimate.EstimateCatalogItemListScenario estimateCatalogItemListScenario;

    @Operation(summary = "Создание сметы для проекта", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/estimates")
    public EstimateResponse create(Principal principal, @RequestBody @Valid EstimateCreateRequest request) {
        return estimateCreateScenario.create(userService.get(principal).getId(), request);
    }

    @Operation(summary = "Получение сметы по id", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/estimates/{id}")
    public EstimateResponse getById(Principal principal, @PathVariable Long id) {
        return estimateGetScenario.getById(userService.get(principal).getId(), id);
    }

    @Operation(summary = "Получение сметы проекта", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/projects/{projectId}/estimate")
    public EstimateResponse getByProject(Principal principal, @PathVariable Long projectId) {
        return estimateGetScenario.getByProject(userService.get(principal).getId(), projectId);
    }

    @Operation(summary = "Удаление сметы", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/estimates/{id}")
    public void delete(Principal principal, @PathVariable Long id) {
        estimateDeleteScenario.delete(userService.get(principal).getId(), id);
    }

    // Позиции сметы

    @Operation(summary = "Список позиций сметы", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/estimates/{estimateId}/items")
    public List<EstimateItemResponse> listItems(Principal principal, @PathVariable Long estimateId) {
        return estimateGetScenario.listItems(userService.get(principal).getId(), estimateId);
    }

    @Operation(summary = "Получение позиции сметы с историей", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/estimates/{estimateId}/items/{itemId}")
    public com.example.multidatasoure.controller.response.EstimateItemWithHistoryResponse getItem(
            Principal principal,
            @PathVariable Long estimateId,
            @PathVariable Long itemId
    ) {
        return estimateItemGetScenario.get(userService.get(principal).getId(), estimateId, itemId);
    }

    @Operation(summary = "Добавление позиции в смету", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/estimates/{estimateId}/items")
    public EstimateItemResponse addItem(Principal principal, @PathVariable Long estimateId,
                                        @RequestBody @Valid EstimateItemCreateRequest request) {
        return estimateItemAddScenario.add(userService.get(principal).getId(), estimateId, request);
    }

    @Operation(summary = "Удаление позиции сметы", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/estimates/{estimateId}/items/{itemId}")
    public void deleteItem(Principal principal, @PathVariable Long estimateId, @PathVariable Long itemId) {
        estimateItemDeleteScenario.delete(userService.get(principal).getId(), estimateId, itemId);
    }

    @Operation(summary = "Частичное обновление позиции сметы", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/estimates/{estimateId}/items/{itemId}")
    public EstimateItemResponse patchItem(Principal principal,
                                          @PathVariable Long estimateId,
                                          @PathVariable Long itemId,
                                          @RequestBody @Valid com.example.multidatasoure.controller.request.EstimateItemPatchRequest request) {
        return estimateItemPatchScenario.patch(userService.get(principal).getId(), estimateId, itemId, request);
    }

    @Operation(summary = "Массовое добавление/обновление позиций сметы (upsert)", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/estimates/{estimateId}/items/bulk-upsert")
    public List<EstimateItemResponse> bulkUpsert(Principal principal,
                                                 @PathVariable Long estimateId,
                                                 @RequestBody @Valid com.example.multidatasoure.controller.request.EstimateItemsUpsertRequest request) {
        return estimateItemsUpsertScenario.upsert(userService.get(principal).getId(), estimateId, request);
    }

    @Operation(summary = "История изменений позиции сметы", security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/estimates/{estimateId}/items/{itemId}/history")
    public java.util.List<com.example.multidatasoure.controller.response.EstimateItemHistoryResponse> history(
            Principal principal,
            @PathVariable Long estimateId,
            @PathVariable Long itemId
    ) {
        return estimateItemHistoryScenario.history(userService.get(principal).getId(), estimateId, itemId);
    }

    // Каталожные позиции в смету
    @io.swagger.v3.oas.annotations.Operation(summary = "Добавление каталожной позиции в смету", security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearer"))
    @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    @org.springframework.web.bind.annotation.PostMapping("/estimates/{estimateId}/catalog-items")
    public com.example.multidatasoure.controller.response.EstimateCatalogItemResponse addCatalogItem(
            Principal principal,
            @PathVariable Long estimateId,
            @jakarta.validation.Valid @org.springframework.web.bind.annotation.RequestBody com.example.multidatasoure.controller.request.EstimateCatalogItemAddRequest request
    ) {
        return estimateCatalogItemAddScenario.add(userService.get(principal).getId(), estimateId, request);
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Список каталожных позиций сметы", security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearer"))
    @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.OK)
    @org.springframework.web.bind.annotation.GetMapping("/estimates/{estimateId}/catalog-items")
    public java.util.List<com.example.multidatasoure.controller.response.EstimateCatalogItemResponse> listCatalogItems(
            Principal principal,
            @PathVariable Long estimateId
    ) {
        return estimateCatalogItemListScenario.list(userService.get(principal).getId(), estimateId);
    }
}
