package com.example.multidatasoure.scenario.estimate;

import com.example.multidatasoure.service.EstimateCatalogItemService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstimateCatalogItemDeleteScenario {
    private final UserService userService;
    private final EstimateCatalogItemService estimateCatalogItemService;

    @Transactional
    public void delete(Long userId, Long estimateId, Long catalogItemId) {
        var user = userService.findById(userId);
        estimateCatalogItemService.delete(user, estimateId, catalogItemId);
    }
}

