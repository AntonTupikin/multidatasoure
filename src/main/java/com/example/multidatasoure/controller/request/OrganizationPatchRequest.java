package com.example.multidatasoure.controller.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record OrganizationPatchRequest(
        @NotBlank
        String title,
        @NotBlank
        Long inn,
        List<Long>usersIds) {
}
