package com.example.multidatasoure.scenario.estimate;

import com.example.multidatasoure.controller.request.EstimateCatalogItemPatchRequest;
import com.example.multidatasoure.controller.response.EstimateCatalogItemResponse;
import com.example.multidatasoure.mapper.EstimateCatalogItemMapper;
import com.example.multidatasoure.service.EstimateCatalogItemService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EstimateCatalogItemPatchScenario {
    private final UserService userService;
    private final EstimateCatalogItemService estimateCatalogItemService;
    private final EstimateCatalogItemMapper mapper;

    @Transactional
    public EstimateCatalogItemResponse patch(Long userId, Long estimateId, Long catalogItemId, EstimateCatalogItemPatchRequest request) {
        var user = userService.findById(userId);
        var link = estimateCatalogItemService.patch(user, estimateId, catalogItemId, request);
        return mapper.toResponse(link);
    }
}

