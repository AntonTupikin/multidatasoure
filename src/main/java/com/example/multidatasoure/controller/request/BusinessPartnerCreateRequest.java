package com.example.multidatasoure.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BusinessPartnerCreateRequest(
        @NotBlank @Size(max = 255) String name
) {}

