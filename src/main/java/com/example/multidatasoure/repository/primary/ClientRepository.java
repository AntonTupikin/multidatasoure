package com.example.multidatasoure.repository.primary;

import com.example.multidatasoure.entity.primary.Client;
import com.example.multidatasoure.entity.primary.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
    Page<Client> findAllByUser(User user, Pageable pageable);

    boolean existsByUserAndPhone(User user, String phone);

    Optional<Client> findByIdAndUser(Long id, User user);

}
