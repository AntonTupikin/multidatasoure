package com.example.multidatasoure.mapper;

import com.example.multidatasoure.controller.response.ClientProfileResponse;
import com.example.multidatasoure.controller.response.ClientResponse;
import com.example.multidatasoure.entity.primary.Client;
import com.example.multidatasoure.entity.primary.ClientProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientResponse toClientResponse(Client client);

    ClientProfileResponse toClientProfileResponse(ClientProfile clientProfile);
}
