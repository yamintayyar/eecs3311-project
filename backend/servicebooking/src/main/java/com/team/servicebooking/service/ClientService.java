package com.team.servicebooking.service;

import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.repository.ClientRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    public Client createClient(String name, String email, String password) {
        Client client = new Client(name, email, password);
        return clientRepository.save(client);
    }

    public Client login(String email, String password) {

        return clientRepository.findAll().stream()
                .filter(c -> c.getEmail().equals(email)
                        && c.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(UUID id) {
        return clientRepository.findById(id);
    }

    @Transactional
    public void deleteClient(UUID id) {
        clientRepository.deleteById(id);
    }

    @Transactional
    public Client updateClient(UUID id, String name, String email, String password) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found: " + id));
        client.setName(name);
        client.setEmail(email);
        client.setPassword(password);
        return clientRepository.save(client);
    }
}