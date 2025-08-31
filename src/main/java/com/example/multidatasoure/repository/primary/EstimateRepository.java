package com.example.multidatasoure.repository.primary;

import com.example.multidatasoure.entity.primary.Estimate;
import com.example.multidatasoure.entity.primary.Project;
import com.example.multidatasoure.entity.primary.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstimateRepository extends JpaRepository<Estimate, Long> {
    boolean existsByProject(Project project);

    Optional<Estimate> findByIdAndProjectOrganizationOwner(Long id, User owner);

    Optional<Estimate> findByProjectIdAndProjectOrganizationOwner(Long projectId, User owner);
}

