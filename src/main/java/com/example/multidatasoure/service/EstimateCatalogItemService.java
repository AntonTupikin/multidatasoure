package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.request.EstimateCatalogItemAddRequest;
import com.example.multidatasoure.entity.primary.*;
import com.example.multidatasoure.exception.BadRequestException;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.repository.primary.CatalogItemRepository;
import com.example.multidatasoure.repository.primary.EstimateCatalogItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateCatalogItemService {
    private final EstimateService estimateService;
    private final CatalogItemRepository catalogItemRepository;
    private final EstimateCatalogItemRepository estimateCatalogItemRepository;

    public EstimateCatalogItem add(User user, Long estimateId, EstimateCatalogItemAddRequest request) {
        Estimate estimate = estimateService.getByIdAndUser(estimateId, user);
        CatalogItem catalogItem = catalogItemRepository.findById(request.catalogItemId())
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.catalog-item"));

        // Prevent duplicates of the same catalog item in a single estimate
        estimateCatalogItemRepository.findByEstimateAndCatalogItem(estimate, catalogItem)
                .ifPresent(e -> { throw new BadRequestException("message.exception.conflict.duplicate-catalog-item-in-estimate"); });

        BigDecimal effectivePrice = request.unitPrice() != null ? request.unitPrice() : catalogItem.getUnitPrice();
        EstimateCatalogItem link = EstimateCatalogItem.builder()
                .estimate(estimate)
                .catalogItem(catalogItem)
                .quantity(request.quantity())
                .unitPrice(effectivePrice == null ? BigDecimal.ZERO : effectivePrice)
                .positionNo(request.positionNo())
                .build();
        return estimateCatalogItemRepository.save(link);
    }

    public List<EstimateCatalogItem> list(User user, Long estimateId) {
        Estimate estimate = estimateService.getByIdAndUser(estimateId, user);
        return estimateCatalogItemRepository.findAllByEstimateOrderByPositionNoAscIdAsc(estimate);
    }
}

