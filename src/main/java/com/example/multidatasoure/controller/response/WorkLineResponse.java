package com.example.multidatasoure.controller.response;

import java.math.BigDecimal;

public record WorkLineResponse(
        Long id,
        Long estimateItemId,
        String materialName,
        String unit,
        BigDecimal qtyPlanned,
        BigDecimal qtyActual,
        String status
) {}

