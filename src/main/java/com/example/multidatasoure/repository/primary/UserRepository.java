package com.example.multidatasoure.repository.primary;

import com.example.multidatasoure.entity.primary.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByIdAndSupervisorId(Long id, Long supervisorId);

    List<User> findAllBySupervisor(User supervisor);

    List<User> findAllByOrganizations_Id(Long orgId);
}
