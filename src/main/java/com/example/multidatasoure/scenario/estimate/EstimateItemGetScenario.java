package com.example.multidatasoure.scenario.estimate;

import com.example.multidatasoure.controller.response.EstimateItemHistoryResponse;
import com.example.multidatasoure.controller.response.EstimateItemResponse;
import com.example.multidatasoure.controller.response.EstimateItemWithHistoryResponse;
import com.example.multidatasoure.entity.primary.EstimateItem;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.EstimateMapper;
import com.example.multidatasoure.service.EstimateItemService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateItemGetScenario {
    private final EstimateItemService estimateItemService;
    private final UserService userService;
    private final EstimateMapper estimateMapper;

    @Transactional(readOnly = true)
    public EstimateItemWithHistoryResponse get(Long userId, Long estimateId, Long itemId) {
        User user = userService.findById(userId);
        EstimateItem item = estimateItemService.getItem(user, estimateId, itemId);
        EstimateItemResponse itemDto = estimateMapper.toItemResponse(item);
        // Историю получаем через сервис, маппинг оставляем в сценарии
        List<EstimateItemHistoryResponse> history = estimateItemService
                .listItemHistoryEntities(item)
                .stream()
                .map(estimateMapper::toItemHistoryResponse)
                .toList();
        return new EstimateItemWithHistoryResponse(itemDto, history);
    }
}
