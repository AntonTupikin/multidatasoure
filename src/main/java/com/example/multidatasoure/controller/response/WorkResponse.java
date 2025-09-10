package com.example.multidatasoure.controller.response;

import java.time.Instant;
import java.util.List;

public record WorkResponse(
        Long id,
        Long employeeId,
        String employeeUsername,
        Long estimateId,
        List<EstimateItemResponse> estimateItems,
        String workStatus,
        Instant plannedStartDate,
        Instant plannedEndDate,
        Instant actualStartDate,
        Instant actualEndDate
) {}
