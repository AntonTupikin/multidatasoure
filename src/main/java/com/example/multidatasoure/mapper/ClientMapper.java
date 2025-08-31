package com.example.multidatasoure.mapper;

import com.example.multidatasoure.controller.response.ClientProfileResponse;
import com.example.multidatasoure.controller.response.ClientResponse;
import com.example.multidatasoure.entity.primary.Client;
import com.example.multidatasoure.entity.primary.ClientProfile;
import com.example.multidatasoure.entity.primary.Individual;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {
    @org.mapstruct.Mapping(target = "clientProfileResponse", source = "clientProfile")
    ClientResponse toClientResponse(Client client);

    default ClientProfileResponse toClientProfileResponse(ClientProfile clientProfile) {
        if (clientProfile == null) return null;
        if (clientProfile instanceof Individual i) {
            return new ClientProfileResponse("INDIVIDUAL", i.getFirstName(), i.getLastName(), i.getInn(), i.getBankAccount());
        }
        return new ClientProfileResponse(clientProfile.getClass().getSimpleName(), null, null, null, null);
    }
}
