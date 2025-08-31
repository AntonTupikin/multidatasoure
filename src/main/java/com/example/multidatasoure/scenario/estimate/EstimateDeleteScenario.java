package com.example.multidatasoure.scenario.estimate;

import com.example.multidatasoure.entity.primary.Estimate;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.service.EstimateService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstimateDeleteScenario {
    private final EstimateService estimateService;
    private final UserService userService;

    @Transactional
    public void delete(Long userId, Long id) {
        User user = userService.findById(userId);
        Estimate estimate = estimateService.getByIdAndUser(id, user);
        estimateService.delete(estimate);
    }
}

