package com.example.multidatasoure.scenario.estimate;

import com.example.multidatasoure.controller.request.EstimateCreateRequest;
import com.example.multidatasoure.controller.response.EstimateResponse;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.EstimateMapper;
import com.example.multidatasoure.service.EstimateService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstimateCreateScenario {
    private final EstimateService estimateService;
    private final UserService userService;
    private final EstimateMapper estimateMapper;

    @Transactional
    public EstimateResponse create(Long userId, EstimateCreateRequest request) {
        log.info("Create estimate for user {} and project {}", userId, request.projectId());
        User user = userService.findById(userId);
        return estimateMapper.toEstimateResponse(estimateService.create(user, request));
    }
}

