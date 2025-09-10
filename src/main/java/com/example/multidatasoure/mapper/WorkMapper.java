package com.example.multidatasoure.mapper;

import com.example.multidatasoure.controller.response.WorkResponse;
import com.example.multidatasoure.entity.primary.Work;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public interface WorkMapper {
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeUsername", source = "employee.username")
    @Mapping(target = "estimateId", source = "estimate.id")
    @Mapping(target = "estimateItemId", source = "estimateItem.id")
    @Mapping(target = "estimateCatalogItemId", source = "estimateCatalogItem.id")
    @Mapping(target = "workStatus", source = "workStatus")
    WorkResponse toResponse(Work entity);

    default Instant toInstant(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.toInstant() : null;
    }
}
