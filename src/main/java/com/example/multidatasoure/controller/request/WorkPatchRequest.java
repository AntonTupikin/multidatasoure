package com.example.multidatasoure.controller.request;

import java.math.BigDecimal;
import java.time.Instant;

public record WorkPatchRequest(
        Long employeeId,
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
