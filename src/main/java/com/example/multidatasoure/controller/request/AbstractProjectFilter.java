package com.example.multidatasoure.controller.request;

import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.Project;
import com.example.multidatasoure.entity.primary.User;
import org.springframework.data.jpa.domain.Specification;

public abstract class AbstractProjectFilter {

    public abstract Specification<Project> getSpecification(User user);

    protected Specification<Project> byOrganizationId(Long organizationId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Project.Fields.organization), organizationId);
    }

    protected Specification<Project> byUser(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Project.Fields.organization).get(Organization.Fields.owner), user);
    }
}
