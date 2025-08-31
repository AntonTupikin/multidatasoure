package com.example.multidatasoure.mapper;

import com.example.multidatasoure.controller.response.ProjectResponse;
import com.example.multidatasoure.entity.primary.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.OffsetDateTime;

@Mapper(componentModel = "spring", uses = {OrganizationMapper.class})
public interface ProjectMapper {
    @Mapping(target="clientId", source="client.id")
    @Mapping(target="employeeId", source="employee.id")
    @Mapping(target="organizationResponse", source="organization")
    ProjectResponse toProjectResponse(Project project);

    default Instant toInstant(OffsetDateTime offsetDateTime) {
        return offsetDateTime.toInstant();
    }
}
