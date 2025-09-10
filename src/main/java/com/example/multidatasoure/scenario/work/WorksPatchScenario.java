package com.example.multidatasoure.scenario.work;

import com.example.multidatasoure.controller.request.WorkPatchRequest;
import com.example.multidatasoure.controller.response.WorkResponse;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.entity.primary.Work;
import com.example.multidatasoure.entity.primary.WorkStatus;
import com.example.multidatasoure.mapper.WorkMapper;
import com.example.multidatasoure.service.UserService;
import com.example.multidatasoure.service.WorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorksPatchScenario {
    private final WorkService workService;
    private final UserService userService;
    private final WorkMapper workMapper;

    @Transactional
    public WorkResponse patch(Long userId, Long worksId, WorkPatchRequest request) {
        log.info("Patch work with id {} start", worksId);
        Work work = workService.getById(worksId);
        User user = userService.findById(userId);

        if (request.employeeId() != null) {
            User employee = userService.findById(request.employeeId());
            workService.setEmployee(work, employee);
        }

        if (request.workStatus() != null) {
            var status = WorkStatus.valueOf(request.workStatus());
            workService.setWorkStatus(work, status);
            if (status == WorkStatus.IN_PROGRESS) {
                workService.setActualStartDate(work, OffsetDateTime.now(ZoneOffset.UTC));
            } else if (status == WorkStatus.DONE) {
                workService.setActualEndDate(work, OffsetDateTime.now(ZoneOffset.UTC));
            }
        }

        log.info("Patch work with id {} ended", worksId);
        return workMapper.toResponse(workService.save(work));
    }
}
