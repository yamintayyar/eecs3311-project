package com.team.servicebooking.controller;

import com.team.servicebooking.dto.PaymentMethodRequestDTO;
import com.team.servicebooking.model.payment.*;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.service.ClientService;
import com.team.servicebooking.service.PaymentMethodStrategyService;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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

    @PostMapping
    public void addPaymentMethodStrategy(@RequestBody PaymentMethodRequestDTO request){

        String type =  request.getPaymentMethodType();
        PaymentMethodStrategy method;

        switch (type){
            case "PAYPAL":
                method = new PayPal(request.getEmail());
                break;

            case "BANK TRANSFER":
                method = new BankTransfer(request.getAccountNumber(), request.getRoutingNumber());
                break;

            case "DEBIT":
                method = new Debit(request.getAccountNumber(), YearMonth.parse(request.getExpiryDate(), DateTimeFormatter.ofPattern("MM/yy")).atEndOfMonth(), request.getCvv());
                break;
            default:
                method = new Credit(request.getAccountNumber(), YearMonth.parse(request.getExpiryDate(), DateTimeFormatter.ofPattern("MM/yy")).atEndOfMonth(), request.getCvv());
                break;
        }

        paymentMethodStrategyService.addPaymentMethod(method);

    }

}
