package com.example.multidatasoure.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OrganizationCreateRequest(
        @NotBlank
        @Size(min = 1, max = 255)
        String title,
        @NotNull
        Long inn) {
}
