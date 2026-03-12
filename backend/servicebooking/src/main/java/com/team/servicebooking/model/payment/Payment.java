package com.team.servicebooking.model.payment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.team.servicebooking.model.booking.Booking;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue
    private UUID paymentId;

    private double amount;

    private String paymentMethodType;

    private LocalDateTime timestamp;

    @OneToOne
    private Booking booking;

    public Payment() {
        // JPA constructor
    }

    public Payment(Booking booking, String paymentMethodType, double amount) {
        this.booking = booking;
        this.paymentMethodType = paymentMethodType;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public Booking getBooking() {
        return booking;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethodType() {
        return paymentMethodType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}