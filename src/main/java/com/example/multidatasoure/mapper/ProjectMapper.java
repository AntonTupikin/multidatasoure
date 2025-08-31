package com.example.multidatasoure.mapper;

import com.example.multidatasoure.controller.response.ProjectResponse;
import com.example.multidatasoure.entity.primary.Project;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", uses = {OrganizationMapper.class})
public interface ProjectMapper {
    ProjectResponse toProjectResponse(Project project);

    default OffsetDateTime toOffsetDateTime(Instant instant) {
        return instant.atOffset(ZoneOffset.UTC);
    }

    default Instant toInstant(OffsetDateTime offsetDateTime) {
        return offsetDateTime.toInstant();
    }
}
