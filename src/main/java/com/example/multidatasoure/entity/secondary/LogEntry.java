package com.example.multidatasoure.entity.secondary;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity used for persisting application log messages in the
 * secondary data source for auditing purposes.
 */
@Data
@Entity
@Table(name = "log_entries")
public class LogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
}
