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
        var ids = request.employeeIds();
        if (ids == null || ids.isEmpty()) {
            project.getEmployees().clear();
        } else {
            // Все работники должны быть подчиненными текущего пользователя
            java.util.List<User> employees = ids.stream()
                    .map(id -> userService.findByIdAndSupervisorId(id, user.getId()))
                    .toList();
            project.setEmployees(employees);
        }
        Project saved = projectService.save(project);
        return projectMapper.toProjectResponse(saved);
    }
}
