package com.example.multidatasoure.mapper;

import com.example.multidatasoure.controller.response.OrganizationResponse;
import com.example.multidatasoure.entity.primary.Organization;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    OrganizationResponse toOrganizationResponse(Organization organization);
}
