package com.example.multidatasoure.mapper;

import com.example.multidatasoure.controller.response.WorkResponse;
import com.example.multidatasoure.entity.primary.EstimateItem;
import com.example.multidatasoure.entity.primary.Work;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.OffsetDateTime;

@Mapper(componentModel = "spring",uses = {EstimateMapper.class})
public interface WorkMapper {
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeUsername", source = "employee.username")
    @Mapping(target = "estimateId", expression = "java(firstEstimateId(entity))")
    @Mapping(target = "workStatus", source = "workStatus")
    WorkResponse toResponse(Work entity);

    default Instant toInstant(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.toInstant() : null;
    }

    default Long firstEstimateId(Work work) {
        EstimateItem ei = work.getEstimateItems() != null && !work.getEstimateItems().isEmpty()
                ? work.getEstimateItems().iterator().next()
                : null;
        return ei != null && ei.getEstimate() != null ? ei.getEstimate().getId() : null;
    }
}
