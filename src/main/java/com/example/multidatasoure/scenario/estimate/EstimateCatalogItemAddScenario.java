package com.example.multidatasoure.scenario.estimate;

import com.example.multidatasoure.controller.request.EstimateCatalogItemAddRequest;
import com.example.multidatasoure.controller.response.EstimateCatalogItemResponse;
import com.example.multidatasoure.entity.primary.EstimateCatalogItem;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.EstimateCatalogItemMapper;
import com.example.multidatasoure.service.EstimateCatalogItemService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstimateCatalogItemAddScenario {
    private final UserService userService;
    private final EstimateCatalogItemService estimateCatalogItemService;
    private final EstimateCatalogItemMapper mapper;

    @Transactional
    public EstimateCatalogItemResponse add(Long userId, Long estimateId, EstimateCatalogItemAddRequest request) {
        User user = userService.findById(userId);
        EstimateCatalogItem link = estimateCatalogItemService.add(user, estimateId, request);
        return mapper.toResponse(link);
    }
}

