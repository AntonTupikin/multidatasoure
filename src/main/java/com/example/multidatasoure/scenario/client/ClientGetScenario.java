package com.example.multidatasoure.scenario.client;

import com.example.multidatasoure.controller.response.ClientResponse;
import com.example.multidatasoure.entity.primary.Client;
import com.example.multidatasoure.mapper.ClientMapper;
import com.example.multidatasoure.service.ClientService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientGetScenario {
    private final ClientService clientService;
    private final UserService userService;
    private final ClientMapper clientMapper;

    @Transactional(readOnly = true)
    public Page<ClientResponse> getAllByUser(Long userId, Pageable pageable) {
        log.info("Get clients for user. {}.", pageable);
        Page<Client> clients = clientService.getAllByUser(userService.findById(userId), pageable);
        return clients.map(clientMapper::toClientResponse);
    }
}
