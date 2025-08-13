package com.example.multidatasoure.service;


import com.example.multidatasoure.entity.secondary.LogEntry;
import com.example.multidatasoure.repository.secondary.LogEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogEntriesService {

    private final LogEntryRepository logEntryRepository;

    public void save(String message) {
        LogEntry logEntry = new LogEntry();
        logEntry.setMessage(message);
        logEntryRepository.save(logEntry);
    }
}
