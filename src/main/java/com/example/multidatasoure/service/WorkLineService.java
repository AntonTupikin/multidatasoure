package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.request.WorkLineCreateRequest;
import com.example.multidatasoure.controller.request.WorkLinePatchRequest;
import com.example.multidatasoure.entity.primary.Estimate;
import com.example.multidatasoure.entity.primary.EstimateItem;
import com.example.multidatasoure.entity.primary.Work;
import com.example.multidatasoure.entity.primary.WorkLine;
import com.example.multidatasoure.entity.primary.WorkLineStatus;
import com.example.multidatasoure.exception.ConflictException;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.repository.primary.EstimateItemRepository;
import com.example.multidatasoure.repository.primary.WorkLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkLineService {
    private final WorkLineRepository workLineRepository;
    private final EstimateItemRepository estimateItemRepository;

    @Transactional
    public List<WorkLine> createPlannedLines(Work work, Estimate estimate, List<WorkLineCreateRequest> requests) {
        return requests.stream().map(r -> createSingle(work, estimate, r)).toList();
    }

    private WorkLine createSingle(Work work, Estimate estimate, WorkLineCreateRequest request) {
        EstimateItem item = estimateItemRepository.findByIdForUpdate(request.estimateItemId())
                .filter(i -> i.getEstimate().getId().equals(estimate.getId()))
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.estimate-item"));

        BigDecimal plannedActive = workLineRepository.sumPlannedByItemAndStatuses(
                item.getId(), java.util.List.of(WorkLineStatus.PLANNED, WorkLineStatus.IN_PROGRESS));
        BigDecimal available = item.getQuantity().subtract(plannedActive);

        BigDecimal requested = request.qtyPlanned() == null ? BigDecimal.ZERO : request.qtyPlanned();
        if (requested.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ConflictException("message.exception.conflict.work-line.non-positive");
        }
        if (requested.compareTo(available) > 0) {
            throw new ConflictException("message.exception.conflict.work-line.overplanned", available);
        }

        WorkLine wl = WorkLine.builder()
                .work(work)
                .estimateItem(item)
                .qtyPlanned(requested)
                .qtyActual(BigDecimal.ZERO)
                .status(WorkLineStatus.PLANNED)
                .build();
        return workLineRepository.save(wl);
    }

    public WorkLine patch(Long id, WorkLinePatchRequest request) {
        WorkLine wl = workLineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.works"));

        if (request.status() != null) {
            wl.setStatus(WorkLineStatus.valueOf(request.status()));
        }
        if (request.qtyPlanned() != null) {
            // When increasing planned, re-check availability
            BigDecimal delta = request.qtyPlanned().subtract(wl.getQtyPlanned());
            if (delta.compareTo(BigDecimal.ZERO) != 0) {
                // Lock item
                EstimateItem item = estimateItemRepository.findByIdForUpdate(wl.getEstimateItem().getId())
                        .orElseThrow(() -> new NotFoundException("message.exception.not-found.estimate-item"));
                BigDecimal plannedActive = workLineRepository.sumPlannedByItemAndStatuses(
                        item.getId(), java.util.List.of(WorkLineStatus.PLANNED, WorkLineStatus.IN_PROGRESS));
                // exclude current line's old planned from total
                plannedActive = plannedActive.subtract(wl.getQtyPlanned());
                BigDecimal available = item.getQuantity().subtract(plannedActive);
                if (request.qtyPlanned().compareTo(available) > 0) {
                    throw new ConflictException("message.exception.conflict.work-line.overplanned", available);
                }
                wl.setQtyPlanned(request.qtyPlanned());
            }
        }
        if (request.qtyActual() != null) {
            if (request.qtyActual().compareTo(wl.getQtyPlanned()) > 0) {
                throw new ConflictException("message.exception.conflict.work-line.actual-gt-planned");
            }
            wl.setQtyActual(request.qtyActual());
        }
        return workLineRepository.save(wl);
    }

    @Transactional(readOnly = true)
    public BigDecimal plannedActiveTotal(Long estimateItemId) {
        return workLineRepository.sumPlannedByItemAndStatuses(estimateItemId, java.util.List.of(WorkLineStatus.PLANNED, WorkLineStatus.IN_PROGRESS));
    }

    @Transactional(readOnly = true)
    public BigDecimal doneTotal(Long estimateItemId) {
        return workLineRepository.sumActualDoneByItem(estimateItemId);
    }
}

