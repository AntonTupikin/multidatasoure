package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.request.OrganizationCreateRequest;
import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.exception.ConflictException;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.repository.primary.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    public List<Organization> getAllByUserAndEmployee(User user, User employee){
        return organizationRepository.findAllByUser(user);
    }
    public List<Organization> getAllByUser(User user){
        return organizationRepository.findAllByUser(user);
    }
    public Organization getByIdAndUser(Long id, User user) {
        return organizationRepository.findByIdAndUser(id, user)
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

    public Organization save(OrganizationCreateRequest organizationCreateRequest, User user) {
        checkOrganizationInn(organizationCreateRequest.inn());
        checkOrganizationTitle(organizationCreateRequest.title());
        Organization organization = new Organization();
        organization.setInn(organizationCreateRequest.inn());
        organization.setTitle(organizationCreateRequest.title());
        organization.setUser(user);
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
