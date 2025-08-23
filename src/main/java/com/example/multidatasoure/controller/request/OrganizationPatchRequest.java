package com.example.multidatasoure.controller.request;

import java.util.List;

public record OrganizationPatchRequest(
        String title,
        Long inn,
        List<Long> usersIds) {
}
