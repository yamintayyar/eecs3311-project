package com.team.servicebooking.controller;

import com.team.servicebooking.dto.ClientRequestDTO;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.service.ClientService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public Client createClient(@RequestBody ClientRequestDTO request) {
        return clientService.createClient(
                request.getName(),
                request.getEmail(),
                request.getPassword());
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable UUID id) {
        return clientService.getClientById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable UUID id) {
        clientService.deleteClient(id);
    }
}