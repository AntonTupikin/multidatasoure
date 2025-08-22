package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.response.UserResponse;
import com.example.multidatasoure.entity.primary.EmployeeProfile;
import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.mapper.UserMapper;
import com.example.multidatasoure.repository.primary.EmployeeProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeProfileRepository employeeProfileRepository;
    private final OrganizationService organizationService;
    private final UserMapper userMapper;

    public EmployeeProfile save(User employee) {
        EmployeeProfile employeeProfile = new EmployeeProfile();
        employeeProfile.setUser(employee);
        employeeProfileRepository.save(employeeProfile);
        return employeeProfile;
    }

    public UserResponse update(User employee, Set<Long> organizationsIds) {
        EmployeeProfile employeeProfile = employeeProfileRepository.findByUser(employee)
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.organization"));//TODO:переделать ошибку
        Set<Organization> organizations = new HashSet<>();
        if(!organizationsIds.isEmpty()) {
            organizationsIds.forEach(id -> {
                organizations.add(organizationService.getByIdAndUser(id, employee.getSupervisor()));
            });
            employeeProfile.setOrganizations(organizations);
        }else {
            employeeProfile.setOrganizations(null);
        }
        return userMapper.toUserResponse(employee);
    }
}
;