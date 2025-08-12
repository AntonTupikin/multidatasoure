package com.example.multidatasoure.entity.primary;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity representing a user stored in the primary data source.
 * Holds authentication details and role information.
 */
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.SUPERVISOR;
}
