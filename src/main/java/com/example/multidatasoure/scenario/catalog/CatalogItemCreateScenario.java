package com.example.multidatasoure.scenario.catalog;

import com.example.multidatasoure.controller.request.CatalogItemCreateRequest;
import com.example.multidatasoure.controller.response.CatalogItemResponse;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.CatalogItemMapper;
import com.example.multidatasoure.service.CatalogItemService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CatalogItemCreateScenario {
    private final UserService userService;
    private final CatalogItemService catalogItemService;
    private final CatalogItemMapper catalogItemMapper;

    @Transactional
    public CatalogItemResponse create(Long userId, CatalogItemCreateRequest request) {
        User owner = userService.findById(userId);
        return catalogItemMapper.toResponse(
                catalogItemService.create(owner, request)
        );
    }
}

