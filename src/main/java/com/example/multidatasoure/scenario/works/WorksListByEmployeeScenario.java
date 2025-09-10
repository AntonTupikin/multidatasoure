package com.example.multidatasoure.scenario.works;

import com.example.multidatasoure.controller.response.WorksResponse;
import com.example.multidatasoure.mapper.WorksMapper;
import com.example.multidatasoure.service.UserService;
import com.example.multidatasoure.service.WorksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorksListByEmployeeScenario {
    private final WorksService worksService;
    private final UserService userService;
    private final WorksMapper worksMapper;

    @Transactional(readOnly = true)
    public List<WorksResponse> list(Long userId, Long employeeId) {
        var employee = userService.findById(employeeId);
        return worksService.getAllByEmployee(employee).stream().map(worksMapper::toResponse).toList();
    }
}

