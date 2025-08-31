package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.request.ClientCreateRequest;
import com.example.multidatasoure.entity.primary.Client;
import com.example.multidatasoure.entity.primary.ClientProfileType;
import com.example.multidatasoure.entity.primary.Individual;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.exception.ConflictException;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.repository.primary.ClientRepository;
import com.example.multidatasoure.repository.primary.IndividualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final IndividualRepository individualRepository;

    public Page<Client> getAllByUser(User user, Pageable pageable) {
        return clientRepository.findAllByUser(user, pageable);
    }

    @Transactional
    public Client create(ClientCreateRequest request, User user) {
        if (clientRepository.existsByUserAndPhone(user, request.phone())) {
            throw new ConflictException("message.exception.conflict.client.phone", request.phone());
        }

        Client client = Client.builder()
                .user(user)
                .phone(request.phone())
                .email(request.email())
                .build();
        client = clientRepository.save(client);

        if (request.profileType() == ClientProfileType.INDIVIDUAL) {
            if (request.individual() == null) {
                throw new ConflictException("message.exception.invalid.client.profile", "INDIVIDUAL profile data is required");
            }
            var p = request.individual();
            Individual individual = Individual.builder()
                    .firstName(p.firstName())
                    .lastName(p.lastName())
                    .inn(p.inn())
                    .bankAccount(p.bankAccount())
                    .build();
            individual.setClient(client);
            individual = individualRepository.save(individual);
            client.setClientProfile(individual);
        } else {
            throw new ConflictException("message.exception.invalid.client.profile", request.profileType());
        }

        return client;
    }

    public void delete(Client client) {
        clientRepository.delete(client);
    }

    public Client getByIdAndUser(Long id, User user) {
        return clientRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.client"));
    }

}
