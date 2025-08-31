package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.request.EstimateCreateRequest;
import com.example.multidatasoure.entity.primary.Estimate;
import com.example.multidatasoure.entity.primary.Project;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.exception.ConflictException;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.repository.primary.EstimateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstimateService {
    private final EstimateRepository estimateRepository;
    private final ProjectService projectService;

    /**
     * Создать смету для проекта. Гарантируется одна смета на проект.
     */
    public Estimate create(User user, EstimateCreateRequest request) {
        Project project = projectService.getByIdAndUser(request.projectId(), user);
        if (estimateRepository.existsByProject(project)) {
            throw new ConflictException("message.exception.conflict.estimate.project", project.getId());
        }
        Estimate estimate = Estimate.builder()
                .project(project)
                .title(request.title())
                .currency(request.currency() == null ? "RUB" : request.currency())
                .notes(request.notes())
                .build();
        return estimateRepository.save(estimate);
    }

    public Estimate getByIdAndUser(Long id, User user) {
        return estimateRepository.findByIdAndProjectOrganizationOwner(id, user)
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.estimate"));
    }

    public Estimate getByProjectIdAndUser(Long projectId, User user) {
        return estimateRepository.findByProjectIdAndProjectOrganizationOwner(projectId, user)
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.estimate"));
    }

    public void delete(Estimate estimate) {
        estimateRepository.delete(estimate);
    }
}

