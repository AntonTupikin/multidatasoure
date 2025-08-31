package com.example.multidatasoure.scenario.client;

import com.example.multidatasoure.entity.primary.Client;
import com.example.multidatasoure.entity.primary.User;
import com.example.multidatasoure.service.ClientService;
import com.example.multidatasoure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientDeleteScenario {
    private final ClientService clientService;
    private final UserService userService;

    @Transactional
    public void delete(Long userId, Long clientId) {
        log.info("Delete client {} for user. {}.", clientId, userId);
        User user = userService.findById(userId);
        Client client = clientService.getByIdAndUser(clientId, user);
        clientService.delete(client);
    }
}
