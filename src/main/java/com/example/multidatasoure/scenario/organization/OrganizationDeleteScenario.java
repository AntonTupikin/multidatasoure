package com.example.multidatasoure.scenario.organization;

import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.service.OrganizationService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrganizationDeleteScenario {
    private final UserService userService;
    private final OrganizationService organizationService;

    @Transactional
    public void delete(Long id, Long userId) {
        log.info("Delete organization for user with id {}", userId);
        User user = userService.findById(userId);
        Organization organization = organizationService.getByIdAndUser(id, user);
        organizationService.delete(organization);
    }
}
