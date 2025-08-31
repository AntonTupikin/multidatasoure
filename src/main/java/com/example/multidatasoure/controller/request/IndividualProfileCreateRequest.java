package com.example.multidatasoure.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IndividualProfileCreateRequest(
        @Size(min = 1, max = 255) @NotBlank String firstName,
        @Size(min = 1, max = 255) @NotBlank String lastName,
        @Size(min = 1, max = 255) @NotBlank String inn,
        @Size(min = 1, max = 255) String bankAccount
) {}

