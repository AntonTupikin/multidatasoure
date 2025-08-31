package com.example.multidatasoure.controller.response;

import java.math.BigDecimal;

/**
 * Ответ по позиции сметы. Поле total = quantity * unitPrice, рассчитывается на лету.
 */
public record EstimateItemResponse(
        Long id,
        String materialName,
        String unit,
        BigDecimal quantity,
        BigDecimal unitPrice,
        String category,
        Integer positionNo,
        BigDecimal total
) {}

