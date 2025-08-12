package com.example.multidatasoure.repository.secondary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.multidatasoure.entity.secondary.LogEntry;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
}
