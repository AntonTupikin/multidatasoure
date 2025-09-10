package com.example.multidatasoure.mapper;

import com.example.multidatasoure.controller.response.EstimateItemHistoryResponse;
import com.example.multidatasoure.controller.response.EstimateItemResponse;
import com.example.multidatasoure.controller.response.EstimateResponse;
import com.example.multidatasoure.entity.primary.Estimate;
import com.example.multidatasoure.entity.primary.EstimateItem;
import com.example.multidatasoure.entity.primary.EstimateItemHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring", uses = {com.example.multidatasoure.mapper.WorkMapper.class})
public interface EstimateMapper {
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "items", expression = "java(toItemResponses(entity.getItems()))")
    @Mapping(target = "works", expression = "java(java.util.List.of())")
    EstimateResponse toEstimateResponse(Estimate entity);

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "items", expression = "java(toItemResponses(entity.getItems()))")
    @Mapping(target = "works", source = "works")
    EstimateResponse toEstimateResponse(Estimate entity, java.util.List<com.example.multidatasoure.entity.primary.Work> works);

    default List<EstimateItemResponse> toItemResponses(List<EstimateItem> items) {
        return items == null ? List.of() : items.stream().map(this::toItemResponse).toList();
    }

    @Mapping(target = "total", expression = "java(calcTotal(entity.getQuantity(), entity.getUnitPrice()))")
    @Mapping(target = "businessPartnerId", source = "businessPartner.id")
    @Mapping(target = "businessPartnerName", source = "businessPartner.name")
    EstimateItemResponse toItemResponse(EstimateItem entity);

    @Mapping(target = "changedBy", source = "changedBy.id")
    @Mapping(target = "changedByUsername", source = "changedBy.username")
    EstimateItemHistoryResponse toItemHistoryResponse(EstimateItemHistory entity);

    default BigDecimal calcTotal(BigDecimal q, BigDecimal p) {
        if (q == null || p == null) return BigDecimal.ZERO;
        return p.multiply(q);
    }
}
