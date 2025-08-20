package com.example.multidatasoure.controller.request;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(@NotBlank
                                    String username,
                                    @NotBlank
                                    String password) {
}
