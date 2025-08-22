package com.example.multidatasoure.controller.request;

import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.User;
import org.springframework.data.jpa.domain.Specification;

public abstract class AbstractEmployeeFilter {

    public abstract Specification<User> getSpecification(User supervisor);

    protected Specification<User> byUser(User supervisor) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(User.Fields.supervisor), supervisor);
    }

    protected Specification<User> byOrganizationId(Long organizationId) {
        return (root, query, criteriaBuilder) -> {
            if (organizationId == null) {
                return null;
            } else {
                return criteriaBuilder.equal(root.get(User.Fields.organizations).get(Organization.Fields.id), organizationId);
            }
        };
    }

}