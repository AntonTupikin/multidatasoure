package com.example.multidatasoure.scenario.works;

import com.example.multidatasoure.controller.request.WorksCreateRequest;
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
public class WorksCreateScenario {
    private final WorksService worksService;
    private final UserService userService;
    private final WorksMapper worksMapper;
    private final EstimateService estimateService;
    private final EstimateItemService estimateItemService;
    private final EstimateCatalogItemRepository estimateCatalogItemRepository;

    @Transactional
    public WorksResponse create(Long userId, Long employeeId, WorksCreateRequest request) {
        User user = userService.findById(userId);
        User employee = userService.findById(employeeId);
        Estimate estimate = estimateService.getByIdAndUser(request.estimateId(), user);
        EstimateItem estimateItem = estimateItemService.getByIdAndEstimate(request.estimateItemId(), estimate);
        EstimateCatalogItem estimateCatalogItem = estimateCatalogItemRepository.findById(request.estimateCatalogItemId())
                .filter(ci -> ci.getEstimate().getId().equals(estimate.getId()))
                .orElseThrow(() -> new com.example.multidatasoure.exception.NotFoundException("message.exception.not-found.estimate-catalog-item"));

        Works entity = worksService.newEntity();
        worksService.setEmployee(entity, employee);
        worksService.setEstimate(entity, estimate);
        worksService.setEstimateItem(entity, estimateItem);
        worksService.setEstimateCatalogItem(entity, estimateCatalogItem);
        worksService.setWorkStatus(entity, WorkStatus.valueOf(request.workStatus()));
        worksService.setPlannedStartDate(entity, request.plannedStartDate());
        worksService.setPlannedEndDate(entity, request.plannedEndDate());
        worksService.setActualStartDate(entity, request.actualStartDate());
        worksService.setActualEndDate(entity, request.actualEndDate());
        worksService.setEstimateItemQuantity(entity, request.estimateItemQuantity() == null ? java.math.BigDecimal.ZERO : request.estimateItemQuantity());
        worksService.setEstimateCatalogItemQuantity(entity, request.estimateCatalogItemQuantity() == null ? java.math.BigDecimal.ZERO : request.estimateCatalogItemQuantity());

        Works saved = worksService.save(entity);
        return worksMapper.toResponse(saved);
    }
}
