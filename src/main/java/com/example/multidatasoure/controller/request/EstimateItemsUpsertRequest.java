package com.example.multidatasoure.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Массовое добавление/обновление позиций сметы.
 * Если передан id — обновляем, если нет — создаем новую позицию.
 */
public record EstimateItemsUpsertRequest(@Valid @Size(min = 1) List<EstimateUpsertItem> items) {
    public record EstimateUpsertItem(
            Long id,
            String materialName,
            String unit,
            java.math.BigDecimal quantity,
            java.math.BigDecimal unitPrice,
            String category,
            Integer positionNo
    ) {}
}

