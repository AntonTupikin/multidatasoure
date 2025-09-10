package com.example.multidatasoure.scenario.works;

import com.example.multidatasoure.controller.request.WorksPatchRequest;
import com.example.multidatasoure.controller.response.WorksResponse;
import com.example.multidatasoure.mapper.WorksMapper;
import com.example.multidatasoure.repository.primary.EstimateCatalogItemRepository;
import com.example.multidatasoure.entity.primary.*;
import com.example.multidatasoure.service.UserService;
import com.example.multidatasoure.service.WorksService;
import com.example.multidatasoure.service.EstimateService;
import com.example.multidatasoure.service.EstimateItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorksPatchScenario {
    private final WorksService worksService;
    private final UserService userService;
    private final WorksMapper worksMapper;
    private final EstimateService estimateService;
    private final EstimateItemService estimateItemService;
    private final EstimateCatalogItemRepository estimateCatalogItemRepository;

    @Transactional
    public WorksResponse patch(Long userId, Long worksId, WorksPatchRequest request) {
        Works entity = worksService.getById(worksId);
        User user = userService.findById(userId);

        if (request.employeeId() != null) {
            User employee = userService.findById(request.employeeId());
            worksService.setEmployee(entity, employee);
        }

        if (request.estimateId() != null) {
            Estimate estimate = estimateService.getByIdAndUser(request.estimateId(), user);
            worksService.setEstimate(entity, estimate);
        }
        if (request.estimateItemId() != null && entity.getEstimate() != null) {
            EstimateItem estimateItem = estimateItemService.getByIdAndEstimate(request.estimateItemId(), entity.getEstimate());
            worksService.setEstimateItem(entity, estimateItem);
        }
        if (request.estimateCatalogItemId() != null && entity.getEstimate() != null) {
            EstimateCatalogItem estimateCatalogItem = estimateCatalogItemRepository.findById(request.estimateCatalogItemId())
                    .filter(ci -> ci.getEstimate().getId().equals(entity.getEstimate().getId()))
                    .orElseThrow(() -> new com.example.multidatasoure.exception.NotFoundException("message.exception.not-found.estimate-catalog-item"));
            worksService.setEstimateCatalogItem(entity, estimateCatalogItem);
        }

        if (request.workStatus() != null) worksService.setWorkStatus(entity, WorkStatus.valueOf(request.workStatus()));
        if (request.plannedStartDate() != null) worksService.setPlannedStartDate(entity, request.plannedStartDate());
        if (request.plannedEndDate() != null) worksService.setPlannedEndDate(entity, request.plannedEndDate());
        if (request.actualStartDate() != null) worksService.setActualStartDate(entity, request.actualStartDate());
        if (request.actualEndDate() != null) worksService.setActualEndDate(entity, request.actualEndDate());
        if (request.estimateItemQuantity() != null) worksService.setEstimateItemQuantity(entity, request.estimateItemQuantity());
        if (request.estimateCatalogItemQuantity() != null) worksService.setEstimateCatalogItemQuantity(entity, request.estimateCatalogItemQuantity());

        Works saved = worksService.save(entity);
        return worksMapper.toResponse(saved);
    }
}
