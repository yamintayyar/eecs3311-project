package com.team.servicebooking.model.payment;

import java.util.UUID;

import com.team.servicebooking.model.booking.Booking;

public class Payment {

    private java.util.UUID payment_id;
    private double amount;
    private Booking booking;
    private PaymentMethodStrategy paymentMethod;

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
