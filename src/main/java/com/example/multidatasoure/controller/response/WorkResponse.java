package com.example.multidatasoure.controller.response;

import java.math.BigDecimal;
import java.time.Instant;

public record WorkResponse(
        Long id,
        Long employeeId,
        String employeeUsername,
        Long estimateId,
        Long estimateItemId,
        Long estimateCatalogItemId,
        String workStatus,
        Instant plannedStartDate,
        Instant plannedEndDate,
        Instant actualStartDate,
        Instant actualEndDate,
        BigDecimal estimateItemQuantity,
        BigDecimal estimateCatalogItemQuantity
) {}
