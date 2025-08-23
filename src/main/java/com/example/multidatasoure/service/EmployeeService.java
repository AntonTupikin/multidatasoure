package com.example.multidatasoure.service;

import com.example.multidatasoure.entity.primary.EmployeeProfile;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.UserMapper;
import com.example.multidatasoure.repository.primary.EmployeeProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}