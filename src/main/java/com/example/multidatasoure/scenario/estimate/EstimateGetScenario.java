package com.example.multidatasoure.scenario.estimate;

import com.example.multidatasoure.controller.response.EstimateItemResponse;
import com.example.multidatasoure.controller.response.EstimateResponse;
import com.example.multidatasoure.entity.primary.Estimate;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.EstimateMapper;
import com.example.multidatasoure.service.EstimateItemService;
import com.example.multidatasoure.service.EstimateService;
import com.example.multidatasoure.service.UserService;
import com.example.multidatasoure.service.WorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstimateGetScenario {
    private final EstimateService estimateService;
    private final EstimateItemService estimateItemService;
    private final UserService userService;
    private final EstimateMapper estimateMapper;
    private final WorkService workService;

    @Transactional(readOnly = true)
    public EstimateResponse getById(Long userId, Long id) {
        User user = userService.findById(userId);
        Estimate estimate = estimateService.getByIdAndUser(id, user);
        var works = workService.getAllByEstimateId(estimate.getId());
        return estimateMapper.toEstimateResponse(estimate, works);
    }

    @Transactional(readOnly = true)
    public EstimateResponse getByProject(Long userId, Long projectId) {
        User user = userService.findById(userId);
        Estimate estimate = estimateService.getByProjectIdAndUser(projectId, user);
        var works = workService.getAllByEstimateId(estimate.getId());
        return estimateMapper.toEstimateResponse(estimate, works);
    }

    @Transactional(readOnly = true)
    public List<EstimateItemResponse> listItems(Long userId, Long estimateId) {
        User user = userService.findById(userId);
        return estimateItemService.listItems(user, estimateId).stream().map(estimateMapper::toItemResponse).toList();
    }
}
