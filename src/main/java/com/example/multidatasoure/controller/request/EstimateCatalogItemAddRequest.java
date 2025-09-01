package com.example.multidatasoure.controller.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record EstimateCatalogItemAddRequest(
        @NotNull Long catalogItemId,
        @NotNull @DecimalMin(value = "0", inclusive = true) BigDecimal quantity,
        BigDecimal unitPrice,
        Integer positionNo
) {}
