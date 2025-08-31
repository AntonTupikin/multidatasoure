package com.example.multidatasoure.controller.request;

import java.math.BigDecimal;

/**
 * Частичное обновление позиции сметы. Все поля опциональны, меняются только непустые.
 */
public record EstimateItemPatchRequest(
        String materialName,
        String unit,
        BigDecimal quantity,
        BigDecimal unitPrice,
        String category,
        Integer positionNo
) {}

