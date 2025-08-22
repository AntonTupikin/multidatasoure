package com.example.multidatasoure.repository.primary;

import com.example.multidatasoure.entity.primary.EmployeeProfile;
import com.example.multidatasoure.entity.primary.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long> {
    Optional<EmployeeProfile> findByUser(User user);

}
