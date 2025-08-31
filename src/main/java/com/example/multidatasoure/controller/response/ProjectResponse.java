package com.example.multidatasoure.controller.response;

import com.example.multidatasoure.entity.primary.ProjectStatus;

import java.time.Instant;
import java.util.List;

public record ProjectResponse(Long id,
                              String title,
                              ProjectStatus projectStatus,
                              Long clientId,
                              List<Long> employeeIds,
                              OrganizationResponse organizationResponse,
                              Instant startDate,
                              Instant endDate) {
}
