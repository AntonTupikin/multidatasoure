package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.request.WorkCreateRequest;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.entity.primary.Work;
import com.example.multidatasoure.entity.primary.WorkLine;
import com.example.multidatasoure.entity.primary.WorkStatus;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.repository.primary.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkRepository workRepository;

    public Work save(User employee, WorkCreateRequest request) {
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
        return workRepository.save(work);
    }

    public Work getByIdAndEmployee(Long id, User employee) {
        return workRepository.findByIdAndEmployee(id, employee)
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.works"));
    }

    public Work getByIdAndEmployeeSupervisor(Long id, User user) {
        return workRepository.findByIdAndEmployee_Supervisor(id, user)
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

    public void setWorkLines(Work work, List<WorkLine> workLines) {
        workLines.forEach(work::addLine);
    }
}
