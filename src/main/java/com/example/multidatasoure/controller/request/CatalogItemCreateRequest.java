package com.example.multidatasoure.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CatalogItemCreateRequest(
        @NotBlank @Size(max = 255) String materialName,
        @Size(max = 50) String unit,
        BigDecimal unitPrice,
        @Size(max = 100) String category,
        Long businessPartnerId
) {}

