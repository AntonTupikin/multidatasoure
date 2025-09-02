package com.example.multidatasoure.scenario.estimate;

import com.example.multidatasoure.controller.request.EstimateItemPatchRequest;
import com.example.multidatasoure.controller.response.EstimateItemResponse;
import com.example.multidatasoure.entity.primary.Estimate;
import com.example.multidatasoure.entity.primary.EstimateItem;
import com.example.multidatasoure.entity.primary.EstimateItemHistory;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.EstimateMapper;
import com.example.multidatasoure.repository.primary.EstimateItemHistoryRepository;
import com.example.multidatasoure.service.EstimateItemService;
import com.example.multidatasoure.service.EstimateService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstimateItemPatchScenario {
    private final EstimateItemService estimateItemService;
    private final UserService userService;
    private final EstimateService estimateService;
    private final EstimateMapper estimateMapper;
    private final EstimateItemHistoryRepository estimateItemHistoryRepository;

    @Transactional
    public EstimateItemResponse patch(Long userId, Long estimateId, Long itemId, EstimateItemPatchRequest request) {
        User user = userService.findById(userId);
        Estimate estimate = estimateService.getByIdAndUser(estimateId, user);
        EstimateItem estimateItem = estimateItemService.getByIdAndEstimate(itemId, estimate);

        // Capture old values to track changes
        String oldUnit = estimateItem.getUnit();
        var oldQuantity = estimateItem.getQuantity();
        var oldUnitPrice = estimateItem.getUnitPrice();
        // setters moved to service
        if (request.materialName() != null) estimateItemService.setMaterialName(estimateItem, request.materialName());
        if (request.unit() != null) estimateItemService.setUnit(estimateItem, request.unit());
        if (request.quantity() != null) estimateItemService.setQuantity(estimateItem, request.quantity());
        if (request.unitPrice() != null) estimateItemService.setUnitPrice(estimateItem, request.unitPrice());
        if (request.category() != null) estimateItemService.setCategory(estimateItem, request.category());
        if (request.positionNo() != null) estimateItemService.setPositionNo(estimateItem, request.positionNo());
        if (request.businessPartnerId() != null) estimateItemService.setBusinessPartner(estimateItem, user, request.businessPartnerId());
        // Write history if tracked fields changed
        boolean changed =
                !Objects.equals(oldUnit, estimateItem.getUnit()) ||
                        !Objects.equals(oldQuantity, estimateItem.getQuantity()) ||
                        !Objects.equals(oldUnitPrice, estimateItem.getUnitPrice());
        if (changed) {
            EstimateItemHistory history = EstimateItemHistory.builder()
                    .item(estimateItem)
                    .changedBy(user)
                    .changedAt(java.time.Instant.now())
                    .oldUnit(oldUnit)
                    .newUnit(estimateItem.getUnit())
                    .oldQuantity(oldQuantity)
                    .newQuantity(estimateItem.getQuantity())
                    .oldUnitPrice(oldUnitPrice)
                    .newUnitPrice(estimateItem.getUnitPrice())
                    .build();
            estimateItemHistoryRepository.save(history);
        }
        return estimateMapper.toItemResponse(estimateItem);
    }
}

