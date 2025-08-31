package com.example.multidatasoure.mapper;

import com.example.multidatasoure.controller.response.ProjectResponse;
import com.example.multidatasoure.entity.primary.Project;
import com.example.multidatasoure.entity.primary.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

@Mapper(componentModel = "spring", uses = {OrganizationMapper.class})
public interface ProjectMapper {
    @Mapping(target="clientId", source="client.id")
    @Mapping(target="employeeIds", expression = "java(toEmployeeIds(project.getEmployees()))")
    @Mapping(target="organizationResponse", source="organization")
    ProjectResponse toProjectResponse(Project project);

    default Instant toInstant(OffsetDateTime offsetDateTime) {
        return offsetDateTime.toInstant();
    }

    default List<Long> toEmployeeIds(List<User> users) {
        return users == null ? List.of() : users.stream().map(User::getId).toList();
    }
}
