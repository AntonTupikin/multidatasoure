package com.example.multidatasoure.service;

import com.example.multidatasoure.entity.primary.*;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.repository.primary.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkRepository workRepository;

    public Work getById(Long id) {
        return workRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.works"));
    }

    public List<Work> getAllByEmployee(User employee) {
        return workRepository.findAllByEmployeeOrderByActualStartDateDescIdDesc(employee);
    }

    public Work save(Work work) {
        return workRepository.save(work);
    }

    public Work newEntity() {
        return new Work();
    }

    public void setEmployee(Work work, User employee) { work.setEmployee(employee); }
    public void setEstimate(Work work, Estimate estimate) { work.setEstimate(estimate); }
    public void setEstimateItem(Work work, EstimateItem estimateItem) { work.setEstimateItem(estimateItem); }
    public void setEstimateCatalogItem(Work work, EstimateCatalogItem estimateCatalogItem) { work.setEstimateCatalogItem(estimateCatalogItem); }
    public void setWorkStatus(Work work, WorkStatus status) { work.setWorkStatus(status); }
    public void setPlannedStartDate(Work work, OffsetDateTime v) { work.setPlannedStartDate(v); }
    public void setPlannedEndDate(Work work, OffsetDateTime v) { work.setPlannedEndDate(v); }
    public void setActualStartDate(Work work, OffsetDateTime v) { work.setActualStartDate(v); }
    public void setActualEndDate(Work work, OffsetDateTime v) { work.setActualEndDate(v); }
    public void setEstimateItemQuantity(Work work, BigDecimal v) { work.setEstimateItemQuantity(v); }
    public void setEstimateCatalogItemQuantity(Work work, BigDecimal v) { work.setEstimateCatalogItemQuantity(v); }
}
