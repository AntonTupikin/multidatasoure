package com.example.multidatasoure.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record UserCreateRequest(@NotBlank
                                @Email
                                String email,
                                @NotBlank
                                @Size(min = 8, max = 255)
                                String password) {

}
