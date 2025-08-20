package com.example.multidatasoure.controller.request;

import jakarta.validation.constraints.Size;

public record UserPatchRequest(
        @Size(min = 1, max = 255)
        String password) {
}
