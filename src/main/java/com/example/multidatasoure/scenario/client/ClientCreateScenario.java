package com.example.multidatasoure.scenario.client;

import com.example.multidatasoure.controller.request.ClientCreateRequest;
import com.example.multidatasoure.controller.response.ClientResponse;
import com.example.multidatasoure.entity.primary.Client;
import com.example.multidatasoure.mapper.ClientMapper;
import com.example.multidatasoure.service.ClientService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientCreateScenario {
    private final ClientService clientService;
    private final UserService userService;
    private final ClientMapper clientMapper;

    @Transactional
    public ClientResponse create(Long userId, ClientCreateRequest request) {
        log.info("Create client for user with id {}", userId);
        Client client = clientService.create(request, userService.findById(userId));
        log.info("Create client for user with id {} finished", userId);
        return clientMapper.toClientResponse(client);
    }
}
