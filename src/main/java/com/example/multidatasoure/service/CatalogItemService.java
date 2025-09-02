package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.request.CatalogItemCreateRequest;
import com.example.multidatasoure.controller.request.CatalogItemPatchRequest;
import com.example.multidatasoure.entity.primary.CatalogItem;
import com.example.multidatasoure.entity.primary.ItemCategory;
import com.example.multidatasoure.entity.primary.UnitOfMeasure;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.repository.primary.CatalogItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

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
                .unit(request.unit() == null ? null : UnitOfMeasure.valueOf(request.unit()))
                .unitPrice(request.unitPrice() == null ? BigDecimal.ZERO : request.unitPrice())
                .category(request.category() == null ? null : ItemCategory.valueOf(request.category()))
                .businessPartner(businessPartnerService.resolveBusinessPartner(owner, request.businessPartnerId()))
                .build();
        return catalogItemRepository.save(item);
    }

    public CatalogItem patch(User owner, Long id, CatalogItemPatchRequest request) {
        CatalogItem item = catalogItemRepository.findById(id)
                .orElseThrow(() -> new com.example.multidatasoure.exception.NotFoundException("message.exception.not-found.catalog-item"));
        if (!Objects.equals(item.getOwner().getId(), owner.getId())) {
            throw new com.example.multidatasoure.exception.NotFoundException("message.exception.not-found.catalog-item");
        }
        if (request.materialName() != null) item.setMaterialName(request.materialName());
        if (request.unit() != null) item.setUnit(UnitOfMeasure.valueOf(request.unit()));
        if (request.unitPrice() != null) item.setUnitPrice(request.unitPrice());
        if (request.category() != null) item.setCategory(ItemCategory.valueOf(request.category()));
        if (request.businessPartnerId() != null) item.setBusinessPartner(businessPartnerService.resolveBusinessPartner(owner, request.businessPartnerId()));
        return catalogItemRepository.save(item);
    }
}
