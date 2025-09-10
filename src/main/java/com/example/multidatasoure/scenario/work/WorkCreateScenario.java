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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        User user = userService.findById(userId);
        User employee = userService.findById(employeeId);
        // validate estimate and item belong together
        Estimate estimate = estimateService.getByIdAndUser(request.estimateId(), user);
        EstimateItem estimateItem = estimateItemService.getByIdAndEstimate(request.estimateItemId(), estimate);
        return workMapper.toResponse(worksService.save(employee, estimateItem, request));
    }
}
