package com.example.multidatasoure.controller.request;

import com.example.multidatasoure.entity.primary.Project;
import com.example.multidatasoure.entity.primary.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectFilter extends AbstractProjectFilter {
    private Long organizationId;

    public Specification<Project> getSpecification(User user) {
        return Specification.where(byUser(user))
                .and(byOrganizationId(organizationId));
    }
}