package com.example.multidatasoure.scenario.estimate;

import com.example.multidatasoure.service.EstimateItemService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstimateItemDeleteScenario {
    private final EstimateItemService estimateItemService;
    private final UserService userService;

    @Transactional
    public void delete(Long userId, Long estimateId, Long itemId) {
        log.info("Start delete estimate item id {} from user id {}", estimateId, userId);
        estimateItemService.deleteItem(userService.findById(userId), estimateId, itemId);
        log.info("Delete estimate item id {} from user id {} finished", estimateId, userId);
    }
}

