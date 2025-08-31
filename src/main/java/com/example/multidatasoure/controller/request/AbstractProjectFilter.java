package com.example.multidatasoure.controller.request;

import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.Project;
import com.example.multidatasoure.entity.primary.User;
import org.springframework.data.jpa.domain.Specification;

public abstract class AbstractProjectFilter {

    public abstract Specification<Project> getSpecification(User user);

    protected Specification<Project> byOrganizationId(Long organizationId) {
        // Если organizationId не задан, не добавляем ограничение в запрос (вернем null-спеку)
        if (organizationId == null) {
            return null;
        }
        // Сравниваем по идентификатору организации, а не по всей сущности
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get(Project.Fields.organization).get(Organization.Fields.id),
                        organizationId
                );
    }

    protected Specification<Project> byUser(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Project.Fields.organization).get(Organization.Fields.owner), user);
    }
}
