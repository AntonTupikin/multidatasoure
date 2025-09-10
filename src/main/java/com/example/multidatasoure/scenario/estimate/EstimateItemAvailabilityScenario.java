package com.example.multidatasoure.scenario.estimate;

import com.example.multidatasoure.controller.response.EstimateItemAvailabilityResponse;
import com.example.multidatasoure.entity.primary.EstimateItem;
import com.example.multidatasoure.service.EstimateItemService;
import com.example.multidatasoure.service.UserService;
import com.example.multidatasoure.service.WorkLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class EstimateItemAvailabilityScenario {
    private final EstimateItemService estimateItemService;
    private final WorkLineService workLineService;
    private final UserService userService;

    @Transactional(readOnly = true)
    public EstimateItemAvailabilityResponse availability(Long userId, Long estimateId, Long itemId) {
        var user = userService.findById(userId);
        EstimateItem item = estimateItemService.getItem(user, estimateId, itemId);
        BigDecimal planned = workLineService.plannedActiveTotal(item.getId());
        BigDecimal done = workLineService.doneTotal(item.getId());
        BigDecimal available = item.getQuantity().subtract(planned);
        return new EstimateItemAvailabilityResponse(item.getQuantity(), planned, done, available);
    }
}

