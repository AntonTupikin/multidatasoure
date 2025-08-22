package com.example.multidatasoure.controller.request;

import com.example.multidatasoure.entity.primary.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeFilter extends AbstractEmployeeFilter {
    private Long supervisorId;
    private Long organizationId;
    private Long organizationIdNot;

    public Specification<User> getSpecification(User supervisor) {
        return Specification.where(byUser(supervisor))
                .and(byOrganizationId(organizationId))
                .and(byOrganizationIdNot(organizationIdNot));
    }
}