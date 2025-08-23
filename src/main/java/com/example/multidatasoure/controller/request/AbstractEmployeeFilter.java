package com.example.multidatasoure.controller.request;

import com.example.multidatasoure.entity.primary.EmployeeProfile;
import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.Role;
import com.example.multidatasoure.entity.primary.User;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public abstract class AbstractEmployeeFilter {

    public abstract Specification<User> getSpecification(User supervisor);

    protected Specification<User> byUser(User supervisor) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(User.Fields.supervisor), supervisor);
    }

    protected Specification<User> byOrganizationId(Long organizationId) {
        return (root, query, cb) -> {
            if (organizationId == null) {
                return null;
            }
            var subquery = query.subquery(Long.class);
            var organization = subquery.from(Organization.class);
            var employeeJoin = organization.join(Organization.Fields.employeesProfiles);
            subquery.select(employeeJoin.get(EmployeeProfile.Fields.user).get(User.Fields.id))
                    .where(cb.equal(organization.get(Organization.Fields.id), organizationId));
            return cb.and(
                    cb.equal(root.get(User.Fields.role), Role.EMPLOYEE),
                    root.get(User.Fields.id).in(subquery)
            );
        };
    }

/*    protected Specification<User> byOrganizationId(Long organizationId) {
        return (root, query, cb) -> organizationId == null ? null :
                cb.equal(
                        root.join(User.Fields.organizations)
                                .get(Organization.Fields.id),
                        organizationId
                );
    }*/

    protected Specification<User> byOrganizationIdNot(Long organizationId) {
        return (root, query, cb) -> {
            if (organizationId == null) {
                return null;
            }
            var subquery = query.subquery(Long.class);
            var subRoot = subquery.from(User.class);
            var organizationJoin = subRoot.join(User.Fields.organizations, JoinType.LEFT);
            subquery.select(subRoot.get(User.Fields.id))
                    .where(cb.and(
                            cb.equal(subRoot.get(User.Fields.id), root.get(User.Fields.id)),
                            cb.equal(organizationJoin.get(Organization.Fields.id), organizationId)
                    ));
            return cb.not(cb.exists(subquery));
        };
    }

}