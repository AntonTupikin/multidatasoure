package com.example.multidatasoure.scenario.estimate;

import com.example.multidatasoure.controller.response.EstimateCatalogItemResponse;
import com.example.multidatasoure.mapper.EstimateCatalogItemMapper;
import com.example.multidatasoure.service.EstimateCatalogItemService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstimateCatalogItemListScenario {
    private final UserService userService;
    private final EstimateCatalogItemService estimateCatalogItemService;
    private final EstimateCatalogItemMapper mapper;

    @Transactional(readOnly = true)
    public List<EstimateCatalogItemResponse> list(Long userId, Long estimateId) {
        var user = userService.findById(userId);
        return estimateCatalogItemService.list(user, estimateId)
                .stream().map(mapper::toResponse).toList();
    }
}

