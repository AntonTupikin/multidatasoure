package com.example.multidatasoure.controller.request;

import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.User;
import org.springframework.data.jpa.domain.Specification;

public abstract class AbstractUserFilter {

    public abstract Specification<User> getSpecification(User user);

    protected Specification<User> bySupervisor(User supervisor) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User.Fields.supervisor), supervisor);
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

    protected Specification<User> byNotOrganizationId(Long notOrganizationId) {
        return (root, query, cb) -> {
            if (notOrganizationId == null) {
                return null; // ничего не фильтруем, если id не передали
            }

            // Подзапрос: существует ли связь этого пользователя с указанной организацией?
            assert query != null;
            var sub = query.subquery(Long.class);
            var u2 = sub.from(User.class);
            var o2 = u2.join(User.Fields.organizations);
            sub.select(u2.get(User.Fields.id))
                    .where(
                            cb.equal(u2, root),
                            cb.equal(o2.get(Organization.Fields.id), notOrganizationId)
                    );

            // Нам нужны пользователи, для которых такой связи НЕ существует
            return cb.not(cb.exists(sub));
        };
    }

    protected Specification<User> notAssignedToProjectId(Long notAssignedToProjectId) {
        return (root, query, cb) -> {
            if (notAssignedToProjectId == null) {
                return null; // ничего не фильтруем, если id не передали
            }

            // Подзапрос: существует ли связь этого пользователя с указанной организацией?
            assert query != null;
            var sub = query.subquery(Long.class);
            var u2 = sub.from(User.class);
            var o2 = u2.join(User.Fields.organizations);
            sub.select(u2.get(Organization.Fields.projects))
                    .where(
                            cb.equal(u2, root),
                            cb.equal(o2.get(Organization.Fields.), notOrganizationId)
                    );

            // Нам нужны пользователи, для которых такой связи НЕ существует
            return cb.not(cb.exists(sub));
        };
    }
}
