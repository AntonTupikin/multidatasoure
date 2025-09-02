package com.example.multidatasoure.controller.request;

import jakarta.annotation.Nullable;

import java.math.BigDecimal;

public record EstimateCatalogItemPatchRequest(
        @Nullable BigDecimal quantity,
        @Nullable BigDecimal unitPrice,
        @Nullable Integer positionNo
) {}

