package com.example.multidatasoure.controller.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

public record WorkCreateRequest(
        @NotNull Long estimateId,
        @NotNull Long estimateItemId,
        @NotNull Long estimateCatalogItemId,
        @NotNull String workStatus,
        Instant plannedStartDate,
        Instant plannedEndDate,
        Instant actualStartDate,
        Instant actualEndDate,
        BigDecimal estimateItemQuantity,
        BigDecimal estimateCatalogItemQuantity
) {}
