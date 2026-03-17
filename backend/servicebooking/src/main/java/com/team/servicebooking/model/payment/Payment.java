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
//    private PaymentMethodStrategy paymentMethod; //do we need to keep a record of the payment method? we might, in order to allow for refunds. if not, we can remove this field
    private boolean refunded = false;

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
        return payment_id;
    }

    public double getAmount() {
        return amount;
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

    public void markRefunded() {
        this.refunded = true;
    }

    public Booking getBooking() {
        return booking;
    }

    public String getPaymentMethodType() {
        return paymentMethodType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
