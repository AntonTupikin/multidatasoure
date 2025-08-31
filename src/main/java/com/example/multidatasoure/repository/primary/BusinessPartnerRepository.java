package com.example.multidatasoure.repository.primary;

import com.example.multidatasoure.entity.primary.BusinessPartner;
import com.example.multidatasoure.entity.primary.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessPartnerRepository extends JpaRepository<BusinessPartner, Long> {
    Optional<BusinessPartner> findByIdAndOwner(Long id, User owner);
    boolean existsByOwnerAndNameIgnoreCase(User owner, String name);
    List<BusinessPartner> findAllByOwner(User owner);
}
