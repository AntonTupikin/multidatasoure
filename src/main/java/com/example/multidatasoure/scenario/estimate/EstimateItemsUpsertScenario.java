package com.example.multidatasoure.scenario.estimate;

import com.example.multidatasoure.controller.request.EstimateItemsUpsertRequest;
import com.example.multidatasoure.controller.response.EstimateItemResponse;
import com.example.multidatasoure.mapper.EstimateMapper;
import com.example.multidatasoure.service.EstimateItemService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstimateItemsUpsertScenario {
    private final EstimateItemService estimateItemService;
    private final UserService userService;
    private final EstimateMapper estimateMapper;

    @Transactional
    public List<EstimateItemResponse> upsert(Long userId, Long estimateId, EstimateItemsUpsertRequest request) {
        return estimateItemService.bulkUpsert(userService.findById(userId), estimateId, request)
                .stream().map(estimateMapper::toItemResponse).toList();
    }
}

