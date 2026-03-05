package com.team.servicebooking.service;

import com.team.servicebooking.model.user.Client;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientService {

    private final Map<UUID, Client> clients = new HashMap<>();

    public Client createClient(String name, String email, String password) {
        UUID id = UUID.randomUUID();
        Client client = new Client(id, name, email, password);
        clients.put(id, client);
        return client;
    }

    public List<Client> getAllClients() {
        return new ArrayList<>(clients.values());
    }

    public Optional<Client> getClientById(UUID id) {
        return Optional.ofNullable(clients.get(id));
    }
}