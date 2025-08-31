package com.example.multidatasoure.scenario.estimate;

import com.example.multidatasoure.controller.response.EstimateItemHistoryResponse;
import com.example.multidatasoure.service.EstimateItemService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateItemHistoryScenario {
    private final EstimateItemService estimateItemService;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<EstimateItemHistoryResponse> history(Long userId, Long estimateId, Long itemId) {
        return estimateItemService.listItemHistory(userService.findById(userId), estimateId, itemId);
    }
}

