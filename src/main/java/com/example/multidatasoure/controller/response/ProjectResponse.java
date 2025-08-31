package com.example.multidatasoure.controller.response;

import com.example.multidatasoure.entity.primary.ProjectStatus;

import java.time.Instant;

public record ProjectResponse(Long id, String title, ProjectStatus projectStatus, Long clientId, OrganizationResponse organizationResponse, Instant startDate, Instant endDate) {
}
