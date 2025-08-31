package com.example.multidatasoure.scenario.project;

import com.example.multidatasoure.entity.primary.Project;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.service.ProjectService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectDeleteScenario {
    private final UserService userService;
    private final ProjectService projectService;

    @Transactional
    public void delete(Long userId, Long projectId) {
        log.info("Delete project {} for user. {}.", projectId, userId);
        User user = userService.findById(userId);
        Project project = projectService.getByIdAndUser(projectId, user);
        projectService.delete(project);
    }
}
