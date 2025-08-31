package com.example.multidatasoure.scenario.partner;

import com.example.multidatasoure.controller.request.BusinessPartnerCreateRequest;
import com.example.multidatasoure.controller.response.BusinessPartnerResponse;
import com.example.multidatasoure.service.BusinessPartnerService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BusinessPartnerCreateScenario {
    private final BusinessPartnerService service;
    private final UserService userService;

    @Transactional
    public BusinessPartnerResponse create(Long userId, BusinessPartnerCreateRequest request) {
        var p = service.create(userService.findById(userId), request);
        return new BusinessPartnerResponse(p.getId(), p.getName());
    }
}

