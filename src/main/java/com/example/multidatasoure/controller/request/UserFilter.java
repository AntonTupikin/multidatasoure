package com.example.multidatasoure.controller.request;

import com.example.multidatasoure.entity.primary.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserFilter extends AbstractUserFilter {
    private Long organizationId;
    private Long notLinkedToOrganizationId;
    private Long projectId;
    private Long notAssignedToProjectId;

    public Specification<User> getSpecification(User user) {
        return Specification.where(bySupervisor(user))
                .and(byNotOrganizationId(notLinkedToOrganizationId))
                .and(byProjectId(projectId))
                .and(byNotAssignedToProjectId(notAssignedToProjectId))
                .and(byOrganizationId(organizationId));
    }
}