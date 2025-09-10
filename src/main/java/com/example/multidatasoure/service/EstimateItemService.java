package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.request.EstimateItemCreateRequest;
import com.example.multidatasoure.controller.request.EstimateItemsUpsertRequest;
import com.example.multidatasoure.controller.response.EstimateItemHistoryResponse;
import com.example.multidatasoure.entity.primary.Estimate;
import com.example.multidatasoure.entity.primary.EstimateItem;
import com.example.multidatasoure.entity.primary.EstimateItemHistory;
import com.example.multidatasoure.entity.primary.ItemCategory;
import com.example.multidatasoure.entity.primary.UnitOfMeasure;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.repository.primary.EstimateItemHistoryRepository;
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
    private final EstimateItemHistoryRepository estimateItemHistoryRepository;
    private final BusinessPartnerService businessPartnerService;

    public EstimateItem addItem(User user, Long estimateId, EstimateItemCreateRequest request) {
        Estimate estimate = estimateService.getByIdAndUser(estimateId, user);
        EstimateItem item = EstimateItem.builder()
                .estimate(estimate)
                .materialName(request.materialName())
                .unit(request.unit() == null ? null : UnitOfMeasure.valueOf(request.unit()))
                .quantity(request.quantity() == null ? BigDecimal.ZERO : request.quantity())
                .unitPrice(request.unitPrice() == null ? BigDecimal.ZERO : request.unitPrice())
                .category(request.category() == null ? null : ItemCategory.valueOf(request.category()))
                .positionNo(request.positionNo())
                .businessPartner(businessPartnerService.resolveBusinessPartner(user, request.businessPartnerId()))
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
        // Detach from works to avoid FK violations and keep works intact
        if (item.getWorks() != null && !item.getWorks().isEmpty()) {
            // Copy to avoid ConcurrentModification on bidirectional unlink
            var worksCopy = new java.util.HashSet<>(item.getWorks());
            for (var work : worksCopy) {
                work.removeEstimateItem(item);
            }
        }
        estimateItemRepository.delete(item);
    }

    public EstimateItem getItem(User user, Long estimateId, Long itemId) {
        Estimate estimate = estimateService.getByIdAndUser(estimateId, user);
        return estimateItemRepository.findById(itemId)
                .filter(i -> Objects.equals(i.getEstimate().getId(), estimate.getId()))
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.estimate-item"));
    }

    public EstimateItem getByIdAndEstimate(Long itemId, Estimate estimate) {
        return estimateItemRepository.findByIdAndEstimate(itemId, estimate)
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.estimate-item"));
    }

    public void setMaterialName(EstimateItem estimateItem, String name) {
        estimateItem.setMaterialName(name);
    }

    public void setUnit(EstimateItem estimateItem, UnitOfMeasure unit) {
        estimateItem.setUnit(unit);
    }

    public void setQuantity(EstimateItem estimateItem, BigDecimal quantity) {
        estimateItem.setQuantity(quantity);
    }

    public void setUnitPrice(EstimateItem estimateItem, BigDecimal unitPrice) {
        estimateItem.setUnitPrice(unitPrice);
    }

    public void setCategory(EstimateItem estimateItem, ItemCategory category) {
        estimateItem.setCategory(category);
    }

    public void setPositionNo(EstimateItem estimateItem, Integer positionNo) {
        estimateItem.setPositionNo(positionNo);
    }

    public void setBusinessPartner(EstimateItem estimateItem, User user, Long businessPartnerId) {
        estimateItem.setBusinessPartner(businessPartnerService.resolveBusinessPartner(user, businessPartnerId));
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
                        .unit(it.unit() == null ? null : UnitOfMeasure.valueOf(it.unit()))
                        .quantity(it.quantity() == null ? BigDecimal.ZERO : it.quantity())
                        .unitPrice(it.unitPrice() == null ? BigDecimal.ZERO : it.unitPrice())
                        .category(it.category() == null ? null : ItemCategory.valueOf(it.category()))
                        .positionNo(it.positionNo())
                        .businessPartner(businessPartnerService.resolveBusinessPartner(user, it.businessPartnerId()))
                        .build();
                return estimateItemRepository.save(item);
            } else {
                EstimateItem existing = estimateItemRepository.findById(it.id())
                        .filter(i -> Objects.equals(i.getEstimate().getId(), estimate.getId()))
                        .orElseThrow(() -> new NotFoundException("message.exception.not-found.estimate-item"));
                UnitOfMeasure oldUnit = existing.getUnit();
                var oldQuantity = existing.getQuantity();
                var oldUnitPrice = existing.getUnitPrice();

                if (it.materialName() != null) existing.setMaterialName(it.materialName());
                if (it.unit() != null) existing.setUnit(UnitOfMeasure.valueOf(it.unit()));
                if (it.quantity() != null) existing.setQuantity(it.quantity());
                if (it.unitPrice() != null) existing.setUnitPrice(it.unitPrice());
                if (it.category() != null) existing.setCategory(ItemCategory.valueOf(it.category()));
                if (it.positionNo() != null) existing.setPositionNo(it.positionNo());
                if (it.businessPartnerId() != null)
                    existing.setBusinessPartner(businessPartnerService.resolveBusinessPartner(user, it.businessPartnerId()));
                EstimateItem saved = estimateItemRepository.save(existing);
                boolean changed =
                        !Objects.equals(oldUnit, saved.getUnit()) ||
                                !Objects.equals(oldQuantity, saved.getQuantity()) ||
                                !Objects.equals(oldUnitPrice, saved.getUnitPrice());
                if (changed) {
                    EstimateItemHistory history = EstimateItemHistory.builder()
                            .item(saved)
                            .changedBy(user)
                            .changedAt(java.time.Instant.now())
                            .oldUnit(oldUnit)
                            .newUnit(saved.getUnit())
                            .oldQuantity(oldQuantity)
                            .newQuantity(saved.getQuantity())
                            .oldUnitPrice(oldUnitPrice)
                            .newUnitPrice(saved.getUnitPrice())
                            .build();
                    estimateItemHistoryRepository.save(history);
                }
                return saved;
            }
        }).toList();
    }


    public java.util.List<EstimateItemHistory> listItemHistoryEntities(EstimateItem item) {
        return estimateItemHistoryRepository.findAllByItemIdOrderByChangedAtDesc(item.getId());
    }
}
