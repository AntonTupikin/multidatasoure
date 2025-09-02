package com.example.multidatasoure.scenario.catalog;

import com.example.multidatasoure.controller.request.CatalogItemPatchRequest;
import com.example.multidatasoure.controller.response.CatalogItemResponse;
import com.example.multidatasoure.mapper.CatalogItemMapper;
import com.example.multidatasoure.service.CatalogItemService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CatalogItemPatchScenario {
    private final UserService userService;
    private final CatalogItemService catalogItemService;
    private final CatalogItemMapper catalogItemMapper;

    @Transactional
    public CatalogItemResponse patch(Long userId, Long id, CatalogItemPatchRequest request) {
        var user = userService.findById(userId);
        var item = catalogItemService.patch(user, id, request);
        return catalogItemMapper.toResponse(item);
    }
}

