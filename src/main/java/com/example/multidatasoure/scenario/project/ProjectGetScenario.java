package com.example.multidatasoure.scenario.project;

import com.example.multidatasoure.controller.request.ProjectFilter;
import com.example.multidatasoure.controller.response.ProjectResponse;
import com.example.multidatasoure.entity.primary.Project;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.ProjectMapper;
import com.example.multidatasoure.service.ProjectService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectGetScenario {
    private final ProjectService projectService;
    private final UserService userService;
    private final ProjectMapper projectMapper;

    @Transactional(readOnly = true)
    public Page<ProjectResponse> getAll(Long userId, Pageable pageable, ProjectFilter projectFilter) {
        log.info("Get projects for user. {}. {}.", pageable, projectFilter);
        Page<Project> projects = projectService.getAllByUser(userService.findById(userId), pageable, projectFilter);
        return projects.map(projectMapper::toProjectResponse);
    }

    //TODO:сделать ProjectDetailedResponse
    @Transactional(readOnly = true)
    public ProjectResponse get(Long userId, Long id) {
        User user = userService.findById(userId);
        return projectMapper.toProjectResponse(projectService.getByIdAndUser(id, user));
    }

}