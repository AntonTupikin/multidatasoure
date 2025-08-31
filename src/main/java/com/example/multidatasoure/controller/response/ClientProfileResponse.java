package com.example.multidatasoure.controller.response;

public record ClientProfileResponse(
        String type,
        String firstName,
        String lastName,
        String inn,
        String bankAccount
) {}

