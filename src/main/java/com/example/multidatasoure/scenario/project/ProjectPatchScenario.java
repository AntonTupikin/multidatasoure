package com.example.multidatasoure.scenario.project;

import com.example.multidatasoure.controller.request.ProjectPatchRequest;
import com.example.multidatasoure.controller.response.ProjectResponse;
import com.example.multidatasoure.entity.primary.Project;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.ProjectMapper;
import com.example.multidatasoure.service.ProjectService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectPatchScenario {
    private final ProjectService projectService;
    private final UserService userService;
    private final ProjectMapper projectMapper;

    @Transactional
    public ProjectResponse patch(Long userId, Long projectId, ProjectPatchRequest request) {
        log.info("Patch project {} by user {} with {}", projectId, userId, request);
        User user = userService.findById(userId);
        Project project = projectService.getByIdAndUser(projectId, user);
        if (request.employeeId() == null) {
            project.setEmployee(null);
        } else {
            // Работник должен быть подчиненным (employee) текущего пользователя
            User employee = userService.findByIdAndSupervisorId(request.employeeId(), user.getId());
            project.setEmployee(employee);
        }
        Project saved = projectService.save(project);
        return projectMapper.toProjectResponse(saved);
    }
}

