package com.example.multidatasoure.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Запрос на добавление позиции в смету.
 */
public record EstimateItemCreateRequest(
        @NotBlank
        @Size(max = 255)
        String materialName,
        @Size(max = 50)
        String unit,
        BigDecimal quantity,
        BigDecimal unitPrice,
        @Size(max = 100)
        String category,
        Integer positionNo,
        Long businessPartnerId
) {}
