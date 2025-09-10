package com.example.multidatasoure.controller.request;

import java.math.BigDecimal;

public record WorkLinePatchRequest(
        BigDecimal qtyPlanned,
        BigDecimal qtyActual,
        String status
) {}

