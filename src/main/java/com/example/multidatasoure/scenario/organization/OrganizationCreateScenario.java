package com.example.multidatasoure.scenario.organization;

import com.example.multidatasoure.controller.request.OrganizationCreateRequest;
import com.example.multidatasoure.controller.response.OrganizationResponse;
import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.OrganizationMapper;
import com.example.multidatasoure.service.OrganizationService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrganizationCreateScenario {
    private final UserService userService;
    private final OrganizationService organizationService;
    private final OrganizationMapper organizationMapper;

    @Transactional
    public OrganizationResponse create(OrganizationCreateRequest organizationCreateRequest, Long userId) {
        log.info("Create organization for user with id {}", userId);
        User owner = userService.findById(userId);
        Organization organization = organizationService.save(organizationCreateRequest, owner);
        log.info("Create manager for user with id {} finished", userId);
        return organizationMapper.toOrganizationResponse(organization);
    }
}
