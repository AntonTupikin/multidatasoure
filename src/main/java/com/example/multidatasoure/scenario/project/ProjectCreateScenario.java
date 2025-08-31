package com.example.multidatasoure.scenario.project;

import com.example.multidatasoure.controller.request.ProjectCreateRequest;
import com.example.multidatasoure.controller.response.ProjectResponse;
import com.example.multidatasoure.entity.primary.Client;
import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.Project;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.ProjectMapper;
import com.example.multidatasoure.service.ClientService;
import com.example.multidatasoure.service.OrganizationService;
import com.example.multidatasoure.service.ProjectService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectCreateScenario {
    private final ProjectService projectService;
    private final UserService userService;
    private final ProjectMapper projectMapper;
    private final OrganizationService organizationService;
    private final ClientService clientService;

    @Transactional
    public ProjectResponse create(Long userId, ProjectCreateRequest request) {
        log.info("Create project for user with id {}", userId);
        User user = userService.findById(userId);
        Organization organization = organizationService.getByIdAndOwner(request.organizationId(), user);
        Client client = clientService.getByIdAndUser(request.clientId(), user);
        Project project = projectService.create(request, client, organization, user);
        log.info("Create project for user with id {} finished", userId);
        return projectMapper.toProjectResponse(project);
    }
}
