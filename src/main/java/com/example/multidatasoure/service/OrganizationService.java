package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.request.OrganizationCreateRequest;
import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.repository.primary.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public Organization save(OrganizationCreateRequest organizationCreateRequest, User user) {
        //checkOrganizationCreateRequest(organizationCreateRequest);
        Organization organization = new Organization();
        organization.setInn(Long.valueOf(organizationCreateRequest.inn()));
        organization.setTitle(organizationCreateRequest.title());
        organization.setUser(user);
        return organizationRepository.save(organization);
    }

/*    private void checkOrganizationCreateRequest (OrganizationCreateRequest organizationCreateRequest) throws BadRequestException {
        if(organizationRepository.existsByInn(organizationCreateRequest.inn())){
            throw new BadRequestException("Organization already exists");
        }
    }*/
}
