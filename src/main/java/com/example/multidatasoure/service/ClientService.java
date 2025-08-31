package com.example.multidatasoure.service;

import com.example.multidatasoure.controller.request.ClientCreateRequest;
import com.example.multidatasoure.entity.primary.Client;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.exception.ConflictException;
import com.example.multidatasoure.exception.NotFoundException;
import com.example.multidatasoure.repository.primary.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public Page<Client> getAllByUser(User user, Pageable pageable) {
        return clientRepository.findAllByUser(user, pageable);
    }

    public Client create(ClientCreateRequest request, User user) {
        if (clientRepository.existsByUserAndPhone(user, request.phone())) {
            throw new ConflictException("message.exception.conflict.client.phone", request.phone());
        }
        Client client = Client.builder()
                .user(user)
                .phone(request.phone())
                .email(request.email())
                .user(user)
                .build();
        return clientRepository.save(client);
    }

    public void delete(Client client) {
        clientRepository.delete(client);
    }

    public Client getByIdAndUser(Long id, User user) {
        return clientRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new NotFoundException("message.exception.not-found.client"));
    }

}
