package com.team.servicebooking.model.payment;

import java.util.*;
import jakarta.persistence.*;

import com.team.servicebooking.model.booking.Booking;

@Entity
public class Payment {

    @Id
    private UUID payment_id;
    private double amount;

    @ManyToOne
    private Booking booking;
    // private PaymentMethodStrategy paymentMethod;
    private String paymentMethodType;

    private Payment(Booking booking, PaymentMethodStrategy paymentMethod) {
        this.payment_id = UUID.randomUUID();
        this.booking = booking;
        this.paymentMethod = paymentMethod;
        this.amount = booking.getPrice();
    }

    public static Payment processPayment(Booking booking, PaymentMethodStrategy paymentMethod)
            throws InterruptedException {

        try {
            // check if payment method is valid, and then ask booking class if it can be
            // paid (if status is confirmed)
            // finally, create payment

            if (!paymentMethod.validate()) {
                System.out.println("Error: Invalid payment method");
                return null;
            }

            if (!booking.payable()) {
                System.out.println(
                        "Error: Booking has not been confirmed yet by consultant. Please await their response.");
                return null;
            }

            System.out.println("Processing payment...");
            Payment payment = new Payment(booking, paymentMethod);
            Thread.sleep(1000);

            System.out.println("Communicating payment...");
            Thread.sleep(1000);

            System.out.println("Payment #" + payment.payment_id + " has been successfully processed.");

            return payment;
        } catch (InterruptedException e) {
            System.out.println("Error: unexpected error occurred. Please try again later.");
            return null;
        }

    }

}
