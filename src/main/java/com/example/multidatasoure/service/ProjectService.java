package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.request.AbstractProjectFilter;
import com.example.multidatasoure.controller.request.ProjectCreateRequest;
import com.example.multidatasoure.entity.primary.Client;
import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.Project;
import com.example.multidatasoure.entity.primary.ProjectStatus;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.exception.ConflictException;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.repository.primary.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Page<Project> getAllByUser(User user, Pageable pageable, AbstractProjectFilter projectFilter) {
        return projectRepository.findAll(projectFilter.getSpecification(user), pageable);
    }

    public Project create(ProjectCreateRequest request, Client client, Organization organization, User user) {
        if (projectRepository.existsByOrganizationAndTitle(organization, request.title())) {
            throw new ConflictException("message.exception.conflict.project.title", request.title());
        }
        Project project = Project.builder()
                .organization(organization)
                .title(request.title())
                .projectStatus(ProjectStatus.DRAFT)
                .client(client)
                .startDate(request.startDate().atOffset(ZoneOffset.UTC))
                .endDate(request.endDate().atOffset(ZoneOffset.UTC))
                .build();
        return projectRepository.save(project);
    }

    public Project getByIdAndUser(Long id, User user) {
        return projectRepository.findByIdAndOrganizationOwner(id, user)
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.project"));
    }

    public void delete(Project project) {
        projectRepository.delete(project);
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public void setEmployees(Project project, List<User> employees, User user) {
        project.setEmployees(employees);
    }
}
