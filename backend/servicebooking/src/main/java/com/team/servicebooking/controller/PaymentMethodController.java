package com.team.servicebooking.controller;

import com.team.servicebooking.model.payment.PaymentMethodStrategy;
import com.team.servicebooking.service.PaymentMethodStrategyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

    private final PaymentMethodStrategyService  paymentMethodStrategyService;


    public PaymentMethodController(PaymentMethodStrategyService paymentMethodStrategyService) {
        this.paymentMethodStrategyService = paymentMethodStrategyService;
    }

    @GetMapping
    public List<PaymentMethodStrategy> getPaymentMethodStrategies(){
        return paymentMethodStrategyService.getAllPaymentMethodStrategies();
    }

}
