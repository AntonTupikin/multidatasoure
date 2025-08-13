package com.example.multidatasoure.repository.secondary;

import com.example.multidatasoure.entity.secondary.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
}
