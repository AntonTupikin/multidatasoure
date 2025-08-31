package com.example.multidatasoure.controller.request;

import com.example.multidatasoure.utils.FieldsUtils;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClientCreateRequest(
        @Size(min = 1, max = 255)
        @NotBlank
        @Email
        String email,
        @Size(min = 1, max = 255)
        @NotBlank
        @Pattern(regexp = FieldsUtils.PHONE_PATTERN)
        String phone) {
}
