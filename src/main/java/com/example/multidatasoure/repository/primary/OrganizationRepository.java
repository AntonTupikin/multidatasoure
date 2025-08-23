package com.example.multidatasoure.repository.primary;

import com.example.multidatasoure.entity.primary.EmployeeProfile;
import com.example.multidatasoure.entity.primary.Organization;
import com.example.multidatasoure.entity.primary.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long>,  JpaSpecificationExecutor<Organization> {
    boolean existsByInn(Long inn);
    boolean existsByTitle(String title);
    Optional<Organization> findByIdAndUser(Long id, User owner);
    List<Organization> findAllByUser(User owner);
    List<Organization> findAllByUserAndEmployeesProfilesContains(User user, EmployeeProfile employee);

}
