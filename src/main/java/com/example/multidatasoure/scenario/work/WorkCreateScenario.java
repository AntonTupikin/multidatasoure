package com.example.multidatasoure.scenario.work;

import com.example.multidatasoure.controller.request.WorkCreateRequest;
import com.example.multidatasoure.controller.response.WorkResponse;
import com.example.multidatasoure.entity.primary.Estimate;
import com.example.multidatasoure.entity.primary.EstimateItem;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.WorkMapper;
import com.example.multidatasoure.service.EstimateItemService;
import com.example.multidatasoure.service.EstimateService;
import com.example.multidatasoure.service.UserService;
import com.example.multidatasoure.service.WorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkCreateScenario {
    private final WorkService worksService;
    private final UserService userService;
    private final WorkMapper workMapper;
    private final EstimateService estimateService;
    private final EstimateItemService estimateItemService;

    @Transactional
    public WorkResponse create(Long userId, Long employeeId, WorkCreateRequest request) {
        log.info("Create work for user {} and employee {}", userId, employeeId);
        User user = userService.findById(userId);
        User employee = userService.findById(employeeId);
        Estimate estimate = estimateService.getByIdAndUser(request.estimateId(), user);
        Set<EstimateItem> estimateItems = request.estimateItemIds().stream().map(id->estimateItemService.getByIdAndEstimate(id,estimate)).collect(Collectors.toSet());
        return workMapper.toResponse(worksService.save(employee, estimateItems, request));
    }
}
