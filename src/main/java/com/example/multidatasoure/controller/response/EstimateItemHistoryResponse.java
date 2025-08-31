package com.example.multidatasoure.controller.response;

import java.math.BigDecimal;
import java.time.Instant;

public record EstimateItemHistoryResponse(
        Long id,
        Long itemId,
        Long changedBy,
        String changedByUsername,
        Instant changedAt,
        String oldUnit,
        String newUnit,
        BigDecimal oldQuantity,
        BigDecimal newQuantity,
        BigDecimal oldUnitPrice,
        BigDecimal newUnitPrice
) {}

