package com.example.multidatasoure.controller.response;

import java.util.List;

/**
 * Ответ по смете, содержит позиции сметы.
 */
public record EstimateResponse(
        Long id,
        Long projectId,
        String title,
        String currency,
        String notes,
        List<EstimateItemResponse> items
) {}

