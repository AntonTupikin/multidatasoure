package com.example.multidatasoure.scenario.partner;

import com.example.multidatasoure.controller.response.BusinessPartnerResponse;
import com.example.multidatasoure.service.BusinessPartnerService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessPartnerListScenario {
    private final BusinessPartnerService service;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<BusinessPartnerResponse> list(Long userId) {
        return service.list(userService.findById(userId)).stream()
                .map(p -> new BusinessPartnerResponse(p.getId(), p.getName()))
                .toList();
    }
}

