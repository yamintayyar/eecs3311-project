package com.team.servicebooking.service;

import com.team.servicebooking.model.booking.Booking;
import com.team.servicebooking.model.payment.Payment;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final BookingService bookingService;
    private final PaymentService paymentService;
    private final ServiceService serviceService;

    public ClientService(ClientRepository clientRepository, BookingService bookingService, PaymentService paymentService, ServiceService serviceService) {
        this.clientRepository = clientRepository;
        this.bookingService = bookingService;
        this.paymentService = paymentService;
        this.serviceService = serviceService;
    }

    @Transactional
    public Client createClient(String name, String email, String password) {
        Client client = new Client(name, email, password);
        return clientRepository.save(client);
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

    @Transactional
    public void addPaymentMethod(UUID client_id, UUID method_id) {
        //TODO
    }

    @Transactional
    public void removePaymentMethod(UUID method_id) {
        //TODO
    }

    @Transactional
    public List<Booking> getBookingHistory(UUID client_id) {
        return bookingService.getBookingsByClient(client_id);
    }

    @Transactional
    public List<Payment> getPaymentHistory(UUID client_id) {
        return paymentService.getPaymentsByClient(client_id);
    }

    @Transactional
    public List<com.team.servicebooking.model.service.Service> browseServices() {
        return serviceService.getAllServices();
    }


}