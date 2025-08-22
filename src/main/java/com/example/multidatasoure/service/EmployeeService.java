package com.example.multidatasoure.service;

import com.example.multidatasoure.entity.primary.EmployeeProfile;
import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.UserMapper;
import com.example.multidatasoure.repository.primary.EmployeeProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

/*    public UserResponse update(User employee, Set<Long> organizationsIds) {
        EmployeeProfile employeeProfile = employeeProfileRepository.findByUser(employee)
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.organization"));//TODO:переделать ошибку

        Set<Organization> currentOrganizations = employeeProfile.getOrganizations();
        Set<Long> existingIds = currentOrganizations.stream().map(Organization::getId).collect(Collectors.toSet());
        Set<Organization>
        // remove organizations whose ids are in organizationsIds
        currentOrganizations.removeIf(org -> organizationsIds.contains(org.getId()));

        // add new organizations that were not previously associated
        organizationsIds.stream()
                .filter(id -> !existingIds.contains(id))
                .forEach(id -> currentOrganizations.add(organizationService.getByIdAndUser(id, employee.getSupervisor())));

        employeeProfileRepository.save(employeeProfile);

        return userMapper.toUserResponse(employee);
    }*/

    public void setOrganizations(User employee, Set<Long> organizationsIds) {
        List<Organization> organizations = organizationsIds.stream().map(id -> organizationService.getByIdAndUser(id, employee.getSupervisor())).toList();
        employee.setOrganizations(organizations);
    }
}
;