package com.example.multidatasoure.controller.request;

import java.math.BigDecimal;

public record WorkPatchRequest(
        Long employeeId,
        Long estimateId,
        Long estimateItemId,
        String workStatus,
        BigDecimal estimateItemQuantity
) {}
