package com.example.multidatasoure.controller.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WorkLineCreateRequest(
        @NotNull Long estimateItemId,
        @NotNull BigDecimal qtyPlanned
) {}

