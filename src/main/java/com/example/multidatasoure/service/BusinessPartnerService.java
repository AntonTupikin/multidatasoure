package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.request.BusinessPartnerCreateRequest;
import com.example.multidatasoure.entity.primary.BusinessPartner;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.repository.primary.BusinessPartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessPartnerService {
    private final BusinessPartnerRepository partnerRepository;

    public BusinessPartner create(User user, BusinessPartnerCreateRequest request) {
        BusinessPartner partner = BusinessPartner.builder()
                .owner(user)
                .name(request.name())
                .build();
        return partnerRepository.save(partner);
    }

    public List<BusinessPartner> list(User user) {
        return partnerRepository.findAllByOwner(user);
    }

    public void delete(User user, Long id) {
        BusinessPartner partner = partnerRepository.findByIdAndOwner(id, user)
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.business-partner"));
        partnerRepository.delete(partner);
    }

    public BusinessPartner getByIdAndUser(User user, Long id) {
        return partnerRepository.findByIdAndOwner(id, user)
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.business-partner"));
    }

    public BusinessPartner resolveBusinessPartner(User user, Long partnerId) {
        if (partnerId == null)
            return null;
        return partnerRepository.findByIdAndOwner(partnerId, user)
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.business-partner"));
    }
}
