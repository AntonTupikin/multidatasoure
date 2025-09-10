package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.request.WorkCreateRequest;
import com.example.multidatasoure.entity.primary.EstimateItem;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.entity.primary.Work;
import com.example.multidatasoure.entity.primary.WorkStatus;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.repository.primary.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkRepository workRepository;

    public Work save(User employee, Set<EstimateItem> estimateItems, WorkCreateRequest request) {
        Work work = new Work();
        work.setEmployee(employee);
        setWorkStatus(work, WorkStatus.PLANNED);
        if (request.plannedStartDate() != null) {
            work.setPlannedStartDate(request.plannedStartDate().atOffset(ZoneOffset.UTC));
        }
        if (request.plannedEndDate() != null) {
            work.setPlannedEndDate(request.plannedEndDate().atOffset(ZoneOffset.UTC));
        }
        if (request.actualStartDate() != null) {
            work.setActualStartDate(request.actualStartDate().atOffset(ZoneOffset.UTC));
        }
        if (request.actualEndDate() != null) {
            work.setActualEndDate(request.actualEndDate().atOffset(ZoneOffset.UTC));
        }
        work.setEstimateItems(estimateItems);
        return workRepository.save(work);
    }

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

    public void setEmployee(Work work, User employee) {
        work.setEmployee(employee);
    }

    public void setWorkStatus(Work work, WorkStatus status) {
        work.setWorkStatus(status);
    }

    public void setPlannedStartDate(Work work, OffsetDateTime v) {
        work.setPlannedStartDate(v);
    }

    public void setPlannedEndDate(Work work, OffsetDateTime v) {
        work.setPlannedEndDate(v);
    }

    public void setActualStartDate(Work work, OffsetDateTime v) {
        work.setActualStartDate(v);
    }

    public void setActualEndDate(Work work, OffsetDateTime v) {
        work.setActualEndDate(v);
    }
}
