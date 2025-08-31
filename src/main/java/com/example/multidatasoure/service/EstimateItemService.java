package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.request.EstimateItemCreateRequest;
import com.example.multidatasoure.controller.request.EstimateItemPatchRequest;
import com.example.multidatasoure.controller.request.EstimateItemsUpsertRequest;
import com.example.multidatasoure.entity.primary.Estimate;
import com.example.multidatasoure.entity.primary.EstimateItem;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.repository.primary.EstimateItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EstimateItemService {
    private final EstimateItemRepository estimateItemRepository;
    private final EstimateService estimateService;

    public EstimateItem addItem(User user, Long estimateId, EstimateItemCreateRequest request) {
        Estimate estimate = estimateService.getByIdAndUser(estimateId, user);
        EstimateItem item = EstimateItem.builder()
                .estimate(estimate)
                .materialName(request.materialName())
                .unit(request.unit())
                .quantity(request.quantity() == null ? BigDecimal.ZERO : request.quantity())
                .unitPrice(request.unitPrice() == null ? BigDecimal.ZERO : request.unitPrice())
                .category(request.category())
                .positionNo(request.positionNo())
                .build();
        return estimateItemRepository.save(item);
    }

    public List<EstimateItem> listItems(User user, Long estimateId) {
        Estimate estimate = estimateService.getByIdAndUser(estimateId, user);
        return estimateItemRepository.findAllByEstimateOrderByPositionNoAscIdAsc(estimate);
    }

    public void deleteItem(User user, Long estimateId, Long itemId) {
        Estimate estimate = estimateService.getByIdAndUser(estimateId, user);
        EstimateItem item = estimateItemRepository.findById(itemId)
                .filter(i -> i.getEstimate().getId().equals(estimate.getId()))
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.estimate-item"));
        estimateItemRepository.delete(item);
    }

    /**
     * Частичное обновление позиции сметы по itemId. Меняются только непустые поля.
     */
    public EstimateItem patchItem(User user, Long estimateId, Long itemId, EstimateItemPatchRequest request) {
        Estimate estimate = estimateService.getByIdAndUser(estimateId, user);
        EstimateItem item = estimateItemRepository.findById(itemId)
                .filter(i -> Objects.equals(i.getEstimate().getId(), estimate.getId()))
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.estimate-item"));
        if (request.materialName() != null) item.setMaterialName(request.materialName());
        if (request.unit() != null) item.setUnit(request.unit());
        if (request.quantity() != null) item.setQuantity(request.quantity());
        if (request.unitPrice() != null) item.setUnitPrice(request.unitPrice());
        if (request.category() != null) item.setCategory(request.category());
        if (request.positionNo() != null) item.setPositionNo(request.positionNo());
        return estimateItemRepository.save(item);
    }

    /**
     * Массовое добавление/обновление: если id есть — обновляем, если нет — создаем.
     */
    public List<EstimateItem> bulkUpsert(User user, Long estimateId, EstimateItemsUpsertRequest request) {
        Estimate estimate = estimateService.getByIdAndUser(estimateId, user);
        return request.items().stream().map(it -> {
            if (it.id() == null) {
                EstimateItem item = EstimateItem.builder()
                        .estimate(estimate)
                        .materialName(it.materialName())
                        .unit(it.unit())
                        .quantity(it.quantity() == null ? BigDecimal.ZERO : it.quantity())
                        .unitPrice(it.unitPrice() == null ? BigDecimal.ZERO : it.unitPrice())
                        .category(it.category())
                        .positionNo(it.positionNo())
                        .build();
                return estimateItemRepository.save(item);
            } else {
                EstimateItem existing = estimateItemRepository.findById(it.id())
                        .filter(i -> Objects.equals(i.getEstimate().getId(), estimate.getId()))
                        .orElseThrow(() -> new NotFoundException("message.exception.not-found.estimate-item"));
                if (it.materialName() != null) existing.setMaterialName(it.materialName());
                if (it.unit() != null) existing.setUnit(it.unit());
                if (it.quantity() != null) existing.setQuantity(it.quantity());
                if (it.unitPrice() != null) existing.setUnitPrice(it.unitPrice());
                if (it.category() != null) existing.setCategory(it.category());
                if (it.positionNo() != null) existing.setPositionNo(it.positionNo());
                return estimateItemRepository.save(existing);
            }
        }).toList();
    }
}
