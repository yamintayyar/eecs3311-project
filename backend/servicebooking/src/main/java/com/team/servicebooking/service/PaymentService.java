package com.team.servicebooking.service;

import org.springframework.stereotype.Service;

import com.team.servicebooking.model.booking.Booking;
import com.team.servicebooking.model.payment.Payment;
import com.team.servicebooking.model.payment.PaymentMethodStrategy;

@Service
public class PaymentService {

    public Payment processPayment(Booking booking, PaymentMethodStrategy strategy)
            throws InterruptedException {

        if (!strategy.validate()) {
            throw new RuntimeException("Invalid payment method");
        }

        if (!booking.payable()) {
            throw new RuntimeException("Booking not confirmed");
        }

//        Payment payment = new Payment(booking, strategy.getClass().getSimpleName());
        Payment payment = Payment.processPayment(booking, strategy);

        return payment;
    }
}