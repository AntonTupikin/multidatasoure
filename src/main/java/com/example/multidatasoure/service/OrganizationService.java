package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.request.OrganizationCreateRequest;
import com.example.multidatasoure.entity.primary.EmployeeProfile;
import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.exception.ConflictException;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.repository.primary.EmployeeProfileRepository;
import com.example.multidatasoure.repository.primary.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final UserService userService;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final EmployeeService employeeService;

    public void setEmployees(Organization organization, List<Long> employees, User user) {
        List<EmployeeProfile> users = employees.stream().map(employee -> employeeService.getByIdAndUser(employee, user)).toList();
        organization.setEmployeesProfiles(users);
    }

    public List<Organization> getAllByUserAndEmployee(User owner, User employee) {
        EmployeeProfile profile = employee.getEmployeeProfile();
        return organizationRepository.findAllByUserAndEmployeesProfilesContains(owner, profile);
    }

    public List<Organization> getAllByUser(User owner) {
        return organizationRepository.findAllByUser(owner);
    }

    public Organization getByIdAndOwner(Long id, User owner) {
        return organizationRepository.findByIdAndUser(id, owner)
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.organization"));
    }

    public void setInn(Organization organization, Long inn) {
        checkOrganizationInn(inn);
        organization.setInn(inn);
    }

    public void setTitle(Organization organization, String title) {
        checkOrganizationTitle(title);
        organization.setTitle(title);
    }

    public Organization save(OrganizationCreateRequest organizationCreateRequest, User owner) {
        checkOrganizationInn(organizationCreateRequest.inn());
        checkOrganizationTitle(organizationCreateRequest.title());
        Organization organization = new Organization();
        organization.setInn(organizationCreateRequest.inn());
        organization.setTitle(organizationCreateRequest.title());
        organization.setUser(owner);
        return organizationRepository.save(organization);
    }

    public void delete(Organization organization) {
        organizationRepository.delete(organization);
    }

    private void checkOrganizationInn(Long inn) {
        if (organizationRepository.existsByInn(inn)) {
            throw new ConflictException("message.exception.conflict.organization.inn", inn);
        }
    }

    private void checkOrganizationTitle(String title) {
        if (organizationRepository.existsByTitle(title)) {
            throw new ConflictException("message.exception.conflict.organization.title", title);
        }
    }
}
