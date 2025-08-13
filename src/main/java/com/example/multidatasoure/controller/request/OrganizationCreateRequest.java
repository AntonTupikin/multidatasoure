package com.example.multidatasoure.controller.request;

import jakarta.validation.constraints.NotBlank;

public record OrganizationCreateRequest(
        @NotBlank
        String title,
        @NotBlank
        String inn) {
}
