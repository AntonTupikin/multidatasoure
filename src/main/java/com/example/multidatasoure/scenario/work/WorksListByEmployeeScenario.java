package com.example.multidatasoure.scenario.work;

import com.example.multidatasoure.controller.response.WorkResponse;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.WorkMapper;
import com.example.multidatasoure.service.UserService;
import com.example.multidatasoure.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorksListByEmployeeScenario {
    private final WorkService workService;
    private final UserService userService;
    private final WorkMapper workMapper;

    @Transactional(readOnly = true)
    public List<WorkResponse> list(Long userId, Long employeeId) {
        User employee = userService.findById(employeeId);
        return workService.getAllByEmployee(employee).stream().map(workMapper::toResponse).toList();
    }
}

