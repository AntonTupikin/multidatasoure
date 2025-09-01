package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.request.CatalogItemCreateRequest;
import com.example.multidatasoure.entity.primary.CatalogItem;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.repository.primary.CatalogItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CatalogItemService {
    private final CatalogItemRepository catalogItemRepository;
    private final BusinessPartnerService businessPartnerService;

    public Page<CatalogItem> list(User owner, Pageable pageable, String query) {
        if (query != null && !query.isBlank()) {
            return catalogItemRepository.findAllByOwnerAndMaterialNameContainingIgnoreCase(owner, query.trim(), pageable);
        }
        return catalogItemRepository.findAllByOwner(owner, pageable);
    }

    public CatalogItem create(User owner, CatalogItemCreateRequest request) {
        CatalogItem item = CatalogItem.builder()
                .owner(owner)
                .materialName(request.materialName())
                .unit(request.unit())
                .unitPrice(request.unitPrice() == null ? BigDecimal.ZERO : request.unitPrice())
                .category(request.category())
                .businessPartner(businessPartnerService.resolveBusinessPartner(owner, request.businessPartnerId()))
                .build();
        return catalogItemRepository.save(item);
    }
}
