package com.example.multidatasoure.controller.request;

import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.Project;
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

    /**
     * Пользователи, назначенные на проект с указанным id.
     */
    protected Specification<User> byProjectId(Long projectId) {
        return (root, query, cb) -> {
            if (projectId == null) {
                return null;
            }
            assert query != null;
            var sub = query.subquery(Long.class);
            var p = sub.from(Project.class);
            var e = p.join(Project.Fields.employees);
            sub.select(e.get(User.Fields.id))
                    .where(
                            cb.equal(p.get(Project.Fields.id), projectId),
                            cb.equal(e, root)
                    );
            return cb.exists(sub);
        };
    }

    /**
     * Пользователи, НЕ назначенные на проект с указанным id.
     */
    protected Specification<User> byNotAssignedToProjectId(Long projectId) {
        return (root, query, cb) -> {
            if (projectId == null) {
                return null;
            }
            assert query != null;
            var sub = query.subquery(Long.class);
            var p = sub.from(Project.class);
            var e = p.join(Project.Fields.employees);
            sub.select(e.get(User.Fields.id))
                    .where(
                            cb.equal(p.get(Project.Fields.id), projectId),
                            cb.equal(e, root)
                    );
            return cb.not(cb.exists(sub));
        };
    }
}
