package com.example.multidatasoure.controller.request;

import jakarta.annotation.Nullable;

import java.math.BigDecimal;

public record CatalogItemPatchRequest(
        @Nullable String materialName,
        @Nullable String unit,
        @Nullable BigDecimal unitPrice,
        @Nullable String category,
        @Nullable Long businessPartnerId
) {}

