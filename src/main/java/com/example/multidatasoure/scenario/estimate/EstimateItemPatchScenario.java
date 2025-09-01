package com.example.multidatasoure.scenario.estimate;

import com.example.multidatasoure.controller.request.EstimateItemPatchRequest;
import com.example.multidatasoure.controller.response.EstimateItemResponse;
import com.example.multidatasoure.entity.primary.Estimate;
import com.example.multidatasoure.entity.primary.EstimateItem;
import com.example.multidatasoure.entity.primary.EstimateItemHistory;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.EstimateMapper;
import com.example.multidatasoure.repository.primary.EstimateItemHistoryRepository;
import com.example.multidatasoure.service.BusinessPartnerService;
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
    private final BusinessPartnerService businessPartnerService;
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

        if (request.materialName() != null) estimateItem.setMaterialName(request.materialName());
        if (request.unit() != null) estimateItem.setUnit(request.unit());
        if (request.quantity() != null) estimateItem.setQuantity(request.quantity());
        if (request.unitPrice() != null) estimateItem.setUnitPrice(request.unitPrice());
        if (request.category() != null) estimateItem.setCategory(request.category());
        if (request.positionNo() != null) estimateItem.setPositionNo(request.positionNo());
        if (request.businessPartnerId() != null) estimateItem.setBusinessPartner(businessPartnerService.resolveBusinessPartner(user, request.businessPartnerId()));
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

