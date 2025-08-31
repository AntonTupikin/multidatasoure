package com.example.multidatasoure.controller;

import com.example.multidatasoure.controller.request.ClientCreateRequest;
import com.example.multidatasoure.controller.response.ClientResponse;
import com.example.multidatasoure.scenario.client.ClientCreateScenario;
import com.example.multidatasoure.scenario.client.ClientDeleteScenario;
import com.example.multidatasoure.scenario.client.ClientGetScenario;
import com.example.multidatasoure.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ClientController {
    private final ClientGetScenario clientGetScenario;
    private final ClientCreateScenario clientCreateScenario;
    private final ClientDeleteScenario clientDeleteScenario;
    private final UserService userService;

    @Operation(
            summary = "Получение всех клиентов пользователя",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/clients")
    public Page<ClientResponse> getAll(Principal principal, @ParameterObject Pageable pageable) {
        return clientGetScenario.getAllByUser(userService.get(principal).getId(), pageable);
    }

    @Operation(
            summary = "Создание клиента",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/clients")
    public ClientResponse create(Principal principal, @RequestBody ClientCreateRequest request) {
        return clientCreateScenario.create(userService.get(principal).getId(), request);
    }

    @Operation(
            summary = "Удаление клиента",
            security = @SecurityRequirement(name = "bearer"))
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/clients/{id}")
    public void delete(Principal principal, @PathVariable Long id) {
        clientDeleteScenario.delete(userService.get(principal).getId(), id);
    }
}
