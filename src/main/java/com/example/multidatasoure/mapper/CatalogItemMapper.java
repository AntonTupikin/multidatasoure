package com.example.multidatasoure.mapper;

import com.example.multidatasoure.controller.response.CatalogItemResponse;
import com.example.multidatasoure.entity.primary.CatalogItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CatalogItemMapper {
    @Mapping(target = "businessPartnerId", source = "businessPartner.id")
    @Mapping(target = "businessPartnerName", source = "businessPartner.name")
    CatalogItemResponse toResponse(CatalogItem entity);
}

