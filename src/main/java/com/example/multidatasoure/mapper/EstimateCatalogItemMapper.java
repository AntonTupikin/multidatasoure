package com.example.multidatasoure.mapper;

import com.example.multidatasoure.controller.response.EstimateCatalogItemResponse;
import com.example.multidatasoure.entity.primary.CatalogItem;
import com.example.multidatasoure.entity.primary.EstimateCatalogItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface EstimateCatalogItemMapper {
    @Mapping(target = "catalogItemId", source = "catalogItem.id")
    @Mapping(target = "materialName", source = "catalogItem.materialName")
    @Mapping(target = "unit", source = "catalogItem.unit")
    @Mapping(target = "category", source = "catalogItem.category")
    @Mapping(target = "businessPartnerId", source = "catalogItem.businessPartner.id")
    @Mapping(target = "businessPartnerName", source = "catalogItem.businessPartner.name")
    @Mapping(target = "total", expression = "java(calcTotal(entity.getQuantity(), entity.getUnitPrice()))")
    EstimateCatalogItemResponse toResponse(EstimateCatalogItem entity);

    default BigDecimal calcTotal(BigDecimal q, BigDecimal p) {
        if (q == null || p == null) return BigDecimal.ZERO;
        return p.multiply(q);
    }
}

