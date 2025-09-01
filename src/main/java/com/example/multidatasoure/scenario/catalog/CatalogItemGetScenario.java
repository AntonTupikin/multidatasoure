package com.example.multidatasoure.scenario.catalog;

import com.example.multidatasoure.controller.response.CatalogItemResponse;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.CatalogItemMapper;
import com.example.multidatasoure.service.CatalogItemService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CatalogItemGetScenario {
    private final UserService userService;
    private final CatalogItemService catalogItemService;
    private final CatalogItemMapper catalogItemMapper;

    @Transactional(readOnly = true)
    public Page<CatalogItemResponse> list(Long userId, Pageable pageable, String query) {
        User owner = userService.findById(userId);
        return catalogItemService.list(owner, pageable, query)
                .map(catalogItemMapper::toResponse);
    }
}

