package com.example.multidatasoure.scenario.partner;

import com.example.multidatasoure.service.BusinessPartnerService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BusinessPartnerDeleteScenario {
    private final BusinessPartnerService service;
    private final UserService userService;

    @Transactional
    public void delete(Long userId, Long id) {
        service.delete(userService.findById(userId), id);
    }
}

