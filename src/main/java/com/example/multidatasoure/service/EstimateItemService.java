package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.request.EstimateItemCreateRequest;
import com.example.multidatasoure.controller.request.EstimateItemPatchRequest;
import com.example.multidatasoure.controller.request.EstimateItemsUpsertRequest;
import com.example.multidatasoure.entity.primary.Estimate;
import com.example.multidatasoure.entity.primary.EstimateItem;
import com.example.multidatasoure.entity.primary.EstimateItemHistory;
import com.example.multidatasoure.entity.primary.BusinessPartner;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.repository.primary.EstimateItemHistoryRepository;
import com.example.multidatasoure.repository.primary.EstimateItemRepository;
import com.example.multidatasoure.repository.primary.BusinessPartnerRepository;
import com.example.multidatasoure.controller.response.EstimateItemHistoryResponse;
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
    private final BusinessPartnerRepository businessPartnerRepository;

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
                .businessPartner(resolveBusinessPartner(user, request.businessPartnerId()))
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

    public EstimateItem getItem(User user, Long estimateId, Long itemId) {
        Estimate estimate = estimateService.getByIdAndUser(estimateId, user);
        return estimateItemRepository.findById(itemId)
                .filter(i -> Objects.equals(i.getEstimate().getId(), estimate.getId()))
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.estimate-item"));
    }

    /**
     * Частичное обновление позиции сметы по itemId. Меняются только непустые поля.
     */
    public EstimateItem patchItem(User user, Long estimateId, Long itemId, EstimateItemPatchRequest request) {
        Estimate estimate = estimateService.getByIdAndUser(estimateId, user);
        EstimateItem item = estimateItemRepository.findById(itemId)
                .filter(i -> Objects.equals(i.getEstimate().getId(), estimate.getId()))
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.estimate-item"));
        // Capture old values to track changes
        String oldUnit = item.getUnit();
        var oldQuantity = item.getQuantity();
        var oldUnitPrice = item.getUnitPrice();

        if (request.materialName() != null) item.setMaterialName(request.materialName());
        if (request.unit() != null) item.setUnit(request.unit());
        if (request.quantity() != null) item.setQuantity(request.quantity());
        if (request.unitPrice() != null) item.setUnitPrice(request.unitPrice());
        if (request.category() != null) item.setCategory(request.category());
        if (request.positionNo() != null) item.setPositionNo(request.positionNo());
        if (request.businessPartnerId() != null) item.setBusinessPartner(resolveBusinessPartner(user, request.businessPartnerId()));
        EstimateItem saved = estimateItemRepository.save(item);
        // Write history if tracked fields changed
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
                        .businessPartner(resolveBusinessPartner(user, it.businessPartnerId()))
                        .build();
                return estimateItemRepository.save(item);
            } else {
                EstimateItem existing = estimateItemRepository.findById(it.id())
                        .filter(i -> Objects.equals(i.getEstimate().getId(), estimate.getId()))
                        .orElseThrow(() -> new NotFoundException("message.exception.not-found.estimate-item"));
                String oldUnit = existing.getUnit();
                var oldQuantity = existing.getQuantity();
                var oldUnitPrice = existing.getUnitPrice();

                if (it.materialName() != null) existing.setMaterialName(it.materialName());
                if (it.unit() != null) existing.setUnit(it.unit());
                if (it.quantity() != null) existing.setQuantity(it.quantity());
                if (it.unitPrice() != null) existing.setUnitPrice(it.unitPrice());
                if (it.category() != null) existing.setCategory(it.category());
                if (it.positionNo() != null) existing.setPositionNo(it.positionNo());
                if (it.businessPartnerId() != null) existing.setBusinessPartner(resolveBusinessPartner(user, it.businessPartnerId()));
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

    public List<EstimateItemHistoryResponse> listItemHistory(User user, Long estimateId, Long itemId) {
        Estimate estimate = estimateService.getByIdAndUser(estimateId, user);
        EstimateItem item = estimateItemRepository.findById(itemId)
                .filter(i -> Objects.equals(i.getEstimate().getId(), estimate.getId()))
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.estimate-item"));
        return estimateItemHistoryRepository.findAllByItemIdOrderByChangedAtDesc(item.getId())
                .stream()
                .map(h -> new EstimateItemHistoryResponse(
                        h.getId(),
                        item.getId(),
                        h.getChangedBy() == null ? null : h.getChangedBy().getId(),
                        h.getChangedBy() == null ? null : h.getChangedBy().getUsername(),
                        h.getChangedAt(),
                        h.getOldUnit(),
                        h.getNewUnit(),
                        h.getOldQuantity(),
                        h.getNewQuantity(),
                        h.getOldUnitPrice(),
                        h.getNewUnitPrice()
                ))
                .toList();
    }

    private BusinessPartner resolveBusinessPartner(User user, Long partnerId) {
        if (partnerId == null) return null;
        return businessPartnerRepository.findByIdAndOwner(partnerId, user)
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.business-partner"));
    }
}
