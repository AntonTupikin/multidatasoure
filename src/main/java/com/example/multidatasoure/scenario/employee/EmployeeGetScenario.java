package com.example.multidatasoure.scenario.employee;

import com.example.multidatasoure.controller.request.EmployeeFilter;
import com.example.multidatasoure.controller.response.UserResponse;
import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.UserMapper;
import com.example.multidatasoure.service.OrganizationService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeGetScenario {
    private final UserService userService;
    private final UserMapper userMapper;
    private final OrganizationService organizationService;

    @Transactional(readOnly = true)
    public List<UserResponse> getAllByOrganization(Long userId, Long organizationId) {
        User user = userService.findById(userId);
        Organization organization = organizationService.getByIdAndOwner(organizationId, user);
        return userService.getAllEmployeesByOrganization(organization).stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllByUser(Long userId) {
        User user = userService.findById(userId);
        return userService.getAllEmployeesByUser(user).stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponse findByIdAndUser(Long id, Long userId) {
        log.info("Find employee with id {} for user id {}", id, userId);
        User user = userService.findById(userId);
        return userMapper.toUserResponse(userService.findByIdAndSupervisorId(id, user.getId()));
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getAllByUser(Long userId, Pageable pageable, EmployeeFilter employeeFilter) {
        log.info("Get employees for user. {}. {}.", pageable, employeeFilter);
        Page<User> users = userService.getAllByUser(userService.findById(userId), pageable, employeeFilter);
        return users.map(userMapper::toUserResponse);
    }

}
