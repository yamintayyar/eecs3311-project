package com.team.servicebooking.controller;

import com.team.servicebooking.model.payment.PaymentMethodStrategy;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.service.ClientService;
import com.team.servicebooking.service.PaymentMethodStrategyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

    private final PaymentMethodStrategyService  paymentMethodStrategyService;
    private final ClientService clientService;


    public PaymentMethodController(PaymentMethodStrategyService paymentMethodStrategyService, ClientService clientService) {
        this.paymentMethodStrategyService = paymentMethodStrategyService;
        this.clientService = clientService;
    }

    @GetMapping
    public List<PaymentMethodStrategy> getPaymentMethodStrategies(){
        return paymentMethodStrategyService.getAllPaymentMethodStrategies();
    }

    @GetMapping("/client/{id}")
    public List<PaymentMethodStrategy> getPaymentMethodStrategiesByClientId(@PathVariable UUID id){
        Client client = clientService.getClientById(id).get();
        return paymentMethodStrategyService.getAllPaymentMethodsByClient(client);
    }

}
