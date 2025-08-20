package com.example.multidatasoure.controller.request;

import jakarta.validation.constraints.NotBlank;

public record OrganizationPatchRequest(
        @NotBlank
        String title,
        @NotBlank
        Long inn) {
}
