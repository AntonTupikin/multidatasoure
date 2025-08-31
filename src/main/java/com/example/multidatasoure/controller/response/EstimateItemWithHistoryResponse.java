package com.example.multidatasoure.controller.response;

import java.util.List;

public record EstimateItemWithHistoryResponse(
        EstimateItemResponse item,
        List<EstimateItemHistoryResponse> history
) {}

