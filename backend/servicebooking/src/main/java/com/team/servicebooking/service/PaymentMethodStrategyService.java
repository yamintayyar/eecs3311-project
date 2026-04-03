package com.team.servicebooking.service;

import com.team.servicebooking.model.payment.PaymentMethodStrategy;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.repository.PaymentMethodStrategyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodStrategyService {

    private final PaymentMethodStrategyRepository repo;

    public PaymentMethodStrategyService(PaymentMethodStrategyRepository repo) {
        this.repo = repo;
    }

    //TODO: set up polymorphic methods that use the subclasses' methods, fix subclass fields so that they are compatible in a single superclass database

    public void addPaymentMethod(PaymentMethodStrategy method) {
        repo.save(method);
    }

    public List<PaymentMethodStrategy> getAllPaymentMethodStrategies() {
        return repo.findAll();
    }

    public List<PaymentMethodStrategy> getAllPaymentMethodsByClient(Client client) {
        return repo.findAllByClient(client);
    }
}
