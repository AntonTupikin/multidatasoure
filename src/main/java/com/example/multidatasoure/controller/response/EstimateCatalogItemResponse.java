package com.example.multidatasoure.controller.response;

import java.math.BigDecimal;

public record EstimateCatalogItemResponse(
        Long id,
        Long catalogItemId,
        String materialName,
        String unit,
        BigDecimal quantity,
        BigDecimal unitPrice,
        String category,
        Integer positionNo,
        BigDecimal total,
        Long businessPartnerId,
        String businessPartnerName
) {}

