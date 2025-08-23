package com.example.multidatasoure.service;

import com.example.multidatasoure.entity.primary.EmployeeOrganization;
import com.example.multidatasoure.entity.primary.EmployeeProfile;
import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.mapper.UserMapper;
import com.example.multidatasoure.repository.primary.EmployeeProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


    public void setOrganizations(User employeeUser, Set<Long> organizationsIds) {
        EmployeeProfile employee = employeeUser.getEmployeeProfile();

        // remove links that are no longer present
        employee.getOrganizations().removeIf(link -> {
            if (!organizationsIds.contains(link.getOrganization().getId())) {
                link.getOrganization().getEmployees().remove(link);
                return true;
            }
            return false;
        });

        // add new links
        organizationsIds.forEach(id -> {
            boolean exists = employee.getOrganizations().stream()
                    .anyMatch(link -> link.getOrganization().getId().equals(id));
            if (!exists) {
                Organization org = organizationService.getByIdAndOwner(id, employeeUser.getSupervisor());
                EmployeeOrganization link = new EmployeeOrganization();
                link.setEmployee(employee);
                link.setOrganization(org);
                employee.getOrganizations().add(link);
                org.getEmployees().add(link);
            }
        });
    }
}