package com.example.multidatasoure.controller.response;

import java.math.BigDecimal;

public record CatalogItemResponse(
        Long id,
        String materialName,
        String unit,
        BigDecimal unitPrice,
        String category,
        Long businessPartnerId,
        String businessPartnerName
) {}

