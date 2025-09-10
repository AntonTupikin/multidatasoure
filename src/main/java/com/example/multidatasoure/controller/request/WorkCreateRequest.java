package com.example.multidatasoure.controller.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record WorkCreateRequest(
        @NotNull Long estimateId,
        @NotNull List<Long> estimateItemIds,
        Long estimateCatalogItemId,
        Instant plannedStartDate,
        Instant plannedEndDate,
        Instant actualStartDate,
        Instant actualEndDate,
        BigDecimal estimateItemQuantity,
        BigDecimal estimateCatalogItemQuantity
) {}
