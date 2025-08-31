package com.example.multidatasoure.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Запрос на создание сметы. Смета создается для проекта, одна на проект.
 */
public record EstimateCreateRequest(
        @NotNull
        Long projectId,
        @Size(max = 255)
        String title,
        // Трёхбуквенный код валюты, напр. RUB, USD
        @Size(min = 3, max = 3)
        String currency,
        String notes
) {}

