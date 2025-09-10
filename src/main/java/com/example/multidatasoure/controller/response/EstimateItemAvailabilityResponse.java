package com.example.multidatasoure.controller.response;

import java.math.BigDecimal;

public record EstimateItemAvailabilityResponse(
        BigDecimal quantity,
        BigDecimal plannedTotal,
        BigDecimal doneTotal,
        BigDecimal available
) {}

