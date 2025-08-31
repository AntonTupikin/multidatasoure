package com.example.multidatasoure.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record ProjectCreateRequest(
        @NotBlank
        @Size(min = 1, max = 255)
        String title,
        @NotNull
        Long organizationId,
        @NotNull
        Long clientId,
        Instant startDate,
        Instant endDate) {
}
