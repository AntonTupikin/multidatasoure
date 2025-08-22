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

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeProfileRepository employeeProfileRepository;
    private final OrganizationService organizationService;
    private final UserMapper userMapper;

    public EmployeeProfile save(User employee, Organization organization) {
        EmployeeProfile employeeProfile = new EmployeeProfile();
        employeeProfile.setUser(employee);
        if(organization.getId()!=null){
            employeeProfile.setOrganizations(List.of(organization));
        }
        employeeProfileRepository.save(employeeProfile);
        return employeeProfile;
    }

    public UserResponse update(User employee, List<Long> organizationsIds) {
        EmployeeProfile employeeProfile = employeeProfileRepository.findById(employee.getId())
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.organization"));//TODO:переделать ошибку
        List<Organization> organizations = new ArrayList<>();
        organizationsIds.forEach(id -> {
            organizations.add(organizationService.getByIdAndUser(id, employee.getSupervisor()));
        });
        employeeProfile.setOrganizations(organizations);
        return userMapper.toUserResponse(employee);
    }
}
;