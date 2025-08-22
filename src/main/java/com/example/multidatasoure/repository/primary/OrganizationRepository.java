package com.example.multidatasoure.repository.primary;

import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    boolean existsByInn(Long inn);
    boolean existsByTitle(String title);
    Optional<Organization> findByIdAndOwner(Long id, User owner);
    List<Organization> findAllByOwner(User owner);
    List<Organization> findAllByOwnerAndEmployeesContaining(User owner, User employee);
}
