package com.example.multidatasoure.service;

import com.example.multidatasoure.entity.primary.CatalogItem;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.repository.primary.CatalogItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CatalogItemService {
    private final CatalogItemRepository catalogItemRepository;

    public Page<CatalogItem> list(User owner, Pageable pageable, String query) {
        if (query != null && !query.isBlank()) {
            return catalogItemRepository.findAllByOwnerAndMaterialNameContainingIgnoreCase(owner, query.trim(), pageable);
        }
        return catalogItemRepository.findAllByOwner(owner, pageable);
    }
}

