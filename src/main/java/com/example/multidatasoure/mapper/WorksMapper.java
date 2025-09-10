package com.example.multidatasoure.mapper;

import com.example.multidatasoure.controller.response.WorksResponse;
import com.example.multidatasoure.entity.primary.Works;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorksMapper {
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeUsername", source = "employee.username")
    @Mapping(target = "estimateId", source = "estimate.id")
    @Mapping(target = "estimateItemId", source = "estimateItem.id")
    @Mapping(target = "estimateCatalogItemId", source = "estimateCatalogItem.id")
    @Mapping(target = "workStatus", expression = "java(entity.getWorkStatus() == null ? null : entity.getWorkStatus().name())")
    WorksResponse toResponse(Works entity);
}
