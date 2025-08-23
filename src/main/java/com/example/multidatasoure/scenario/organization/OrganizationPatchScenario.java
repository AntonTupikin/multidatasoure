package com.example.multidatasoure.scenario.organization;

import com.example.multidatasoure.controller.request.OrganizationPatchRequest;
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
public class OrganizationPatchScenario {
    private final UserService userService;
    private final OrganizationService organizationService;
    private final OrganizationMapper organizationMapper;

    @Transactional
    public OrganizationResponse patch(Long id, OrganizationPatchRequest request, Long userId) {
        log.info("Patch organization for user with id {}", userId);
        User user = userService.findById(userId);
        Organization organization = organizationService.getByIdAndOwner(id, user);
        if (request.title() != null) {
            organizationService.setTitle(organization, request.title());
        }
        if (request.inn() != null) {
            organizationService.setInn(organization, request.inn());
        }
        if (request.usersIds() != null) {
            organizationService.setEmployees(organization, request.usersIds(), user);
        }
        log.info("Patch manager for user with id {} finished", userId);
        return organizationMapper.toOrganizationResponse(organization);
    }
}
