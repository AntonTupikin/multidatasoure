package com.example.multidatasoure.scenario.work;

import com.example.multidatasoure.controller.request.WorkLinePatchRequest;
import com.example.multidatasoure.controller.response.WorkLineResponse;
import com.example.multidatasoure.entity.primary.WorkLine;
import com.example.multidatasoure.service.WorkLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkLinePatchScenario {
    private final WorkLineService workLineService;

    @Transactional
    public WorkLineResponse patch(Long userId, Long lineId, WorkLinePatchRequest request) {
        WorkLine wl = workLineService.patch(lineId, request);
        var item = wl.getEstimateItem();
        return new WorkLineResponse(
                wl.getId(),
                item != null ? item.getId() : null,
                item != null ? item.getMaterialName() : null,
                item != null && item.getUnit() != null ? item.getUnit().name() : null,
                wl.getQtyPlanned(),
                wl.getQtyActual(),
                wl.getStatus() != null ? wl.getStatus().name() : null
        );
    }
}

