package com.example.multidatasoure.scenario.employee;

import com.example.multidatasoure.controller.request.UserCreateRequest;
import com.example.multidatasoure.controller.response.UserResponse;
import com.example.multidatasoure.entity.primary.Role;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.UserMapper;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeCreateScenario {
    private final UserService userService;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse create(UserCreateRequest request, Long userId) {
        log.info("Create employee %s for user with id %d".formatted(request.toString(), userId));
        User supervisor = userService.findById(userId);
        User user = userService.save(request, Role.EMPLOYEE, supervisor);
        return userMapper.toUserResponse(user);
    }
}
