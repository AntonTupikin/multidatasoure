package com.example.multidatasoure.mapper;

import com.example.multidatasoure.controller.response.WorkLineResponse;
import com.example.multidatasoure.controller.response.WorkResponse;
import com.example.multidatasoure.entity.primary.Work;
import com.example.multidatasoure.entity.primary.WorkLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

@Mapper(componentModel = "spring",uses = {EstimateMapper.class})
public interface WorkMapper {
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeUsername", source = "employee.username")
    @Mapping(target = "estimateId", expression = "java(firstEstimateId(entity))")
    @Mapping(target = "lines", expression = "java(toLineResponses(entity.getLines()))")
    @Mapping(target = "workStatus", source = "workStatus")
    WorkResponse toResponse(Work entity);

    default Instant toInstant(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.toInstant() : null;
    }

    default Long firstEstimateId(Work work) {
        if (work.getLines() != null && !work.getLines().isEmpty()) {
            var ei = work.getLines().get(0).getEstimateItem();
            return ei != null && ei.getEstimate() != null ? ei.getEstimate().getId() : null;
        }
        return null;
    }

    default List<WorkLineResponse> toLineResponses(List<WorkLine> lines) {
        return lines == null ? java.util.List.of() : lines.stream().map(this::toLineResponse).toList();
    }

    default WorkLineResponse toLineResponse(WorkLine line) {
        var item = line.getEstimateItem();
        return new WorkLineResponse(
                line.getId(),
                item != null ? item.getId() : null,
                item != null ? item.getMaterialName() : null,
                item != null && item.getUnit() != null ? item.getUnit().name() : null,
                line.getQtyPlanned(),
                line.getQtyActual(),
                line.getStatus() != null ? line.getStatus().name() : null
        );
    }
}
