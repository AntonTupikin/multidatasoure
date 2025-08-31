package com.example.multidatasoure.mapper;

import com.example.multidatasoure.controller.response.EstimateItemResponse;
import com.example.multidatasoure.controller.response.EstimateResponse;
import com.example.multidatasoure.entity.primary.Estimate;
import com.example.multidatasoure.entity.primary.EstimateItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EstimateMapper {
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "items", expression = "java(toItemResponses(entity.getItems()))")
    EstimateResponse toEstimateResponse(Estimate entity);

    default List<EstimateItemResponse> toItemResponses(List<EstimateItem> items) {
        return items == null ? List.of() : items.stream().map(this::toItemResponse).toList();
    }

    @Mapping(target = "total", expression = "java(calcTotal(entity.getQuantity(), entity.getUnitPrice()))")
    EstimateItemResponse toItemResponse(EstimateItem entity);

    default BigDecimal calcTotal(BigDecimal q, BigDecimal p) {
        if (q == null || p == null) return BigDecimal.ZERO;
        return p.multiply(q);
    }
}

