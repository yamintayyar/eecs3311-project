package com.team.servicebooking.model.payment;

import com.team.servicebooking.model.booking.Booking;

public class Payment {

    private java.util.UUID payment_id;
    private double amount;
    private Booking booking;
    private String payment_status;
    private PaymentMethodStrategy paymentMethod;

    Payment() {

    }

    void processPayment() {

    }

    String getStatus() {
        return null;
    }
}
