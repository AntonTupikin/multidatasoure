package com.example.multidatasoure.controller.response;

import java.util.List;

public record OrganizationResponse(Long id, String title, Long inn, List<UserResponse>users) {
}
