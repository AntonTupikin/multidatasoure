package com.example.multidatasoure.scenario.estimate;

import com.example.multidatasoure.controller.request.EstimateItemPatchRequest;
import com.example.multidatasoure.controller.response.EstimateItemResponse;
import com.example.multidatasoure.mapper.EstimateMapper;
import com.example.multidatasoure.service.EstimateItemService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstimateItemPatchScenario {
    private final EstimateItemService estimateItemService;
    private final UserService userService;
    private final EstimateMapper estimateMapper;

    @Transactional
    public EstimateItemResponse patch(Long userId, Long estimateId, Long itemId, EstimateItemPatchRequest request) {
        return estimateMapper.toItemResponse(
                estimateItemService.patchItem(userService.findById(userId), estimateId, itemId, request)
        );
    }
}

