package com.example.multidatasoure.scenario.organization;

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

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrganizationGetScenario {
    private final OrganizationService organizationService;
    private final UserService userService;
    private final OrganizationMapper organizationMapper;

    @Transactional
    public List<OrganizationResponse> getAllByUser(Long userId) {
        User user = userService.findById(userId);
        //PasswordResetToken passwordResetToken = authenticationService.createPasswordResetTokenForUser(user);
        //registrationRequestService.updateRegistrationRequest(user);
        return organizationService.getAllByUser(user).stream().map(organizationMapper::toOrganizationResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrganizationResponse findByIdAndUser(Long id, Long userId) {
        log.info("Find organization with id {} for user with id {}", id, userId);
        Organization organization = organizationService.getByIdAndUser(id, userService.findById(userId));
        return organizationMapper.toOrganizationResponse(organization);
    }
}
