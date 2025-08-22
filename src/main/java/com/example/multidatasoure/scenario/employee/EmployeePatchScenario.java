package com.example.multidatasoure.scenario.employee;

import com.example.multidatasoure.controller.request.EmployeePatchRequest;
import com.example.multidatasoure.controller.response.UserResponse;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.UserMapper;
import com.example.multidatasoure.service.EmployeeService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeePatchScenario {
    private final UserMapper userMapper;
    private final UserService userService;
    private final EmployeeService employeeService;

    @Transactional
    public UserResponse patch(Long id, EmployeePatchRequest request, Long userId) {
        log.info("Patch employee for user with id {}", userId);
        User user = userService.findById(userId);
        User employee = userService.findByIdAndSupervisorId(id, user.getId());
        employeeService.setOrganizations(employee, request.organizationIds());
        log.info("Patch employee {} for user with id {} finished", employee.getId(), request);
        return userMapper.toUserResponse(employee);
    }
}
