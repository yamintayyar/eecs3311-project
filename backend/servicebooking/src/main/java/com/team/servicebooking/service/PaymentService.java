package com.team.servicebooking.service;

import com.team.servicebooking.dto.PaymentRequestDTO;
import com.team.servicebooking.model.booking.Booking;
import com.team.servicebooking.model.payment.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PaymentService {

    private final Map<UUID, Payment> payments = new HashMap<>();
    private final Map<UUID, Booking> bookings = new HashMap<>();

    /**
     * Register a booking so it can be referenced when processing payments.
     * This is a temporary solution until a proper BookingService is created.
     */
    public void registerBooking(Booking booking) {
        bookings.put(booking.getID(), booking);
    }

    public Payment processPayment(PaymentRequestDTO request) throws InterruptedException {
        UUID bookingId = UUID.fromString(request.getBookingId());
        Booking booking = bookings.get(bookingId);

        if (booking == null) {
            throw new RuntimeException("Booking not found with id: " + bookingId);
        }

        PaymentMethodStrategy paymentMethod = buildPaymentMethod(request);

        Payment payment = Payment.processPayment(booking, paymentMethod);

        if (payment == null) {
            throw new RuntimeException("Payment processing failed. Check payment method validity and booking status.");
        }

        payments.put(payment.getPaymentId(), payment);
        return payment;
    }

    public List<Payment> getAllPayments() {
        return new ArrayList<>(payments.values());
    }

    public Optional<Payment> getPaymentById(UUID id) {
        return Optional.ofNullable(payments.get(id));
    }

    private PaymentMethodStrategy buildPaymentMethod(PaymentRequestDTO request) {
        String type = request.getPaymentMethodType().toUpperCase();

        switch (type) {
            case "CREDIT":
                return new Credit(
                        request.getCardNumber(),
                        LocalDate.parse(request.getExpiryDate()),
                        request.getCvv());
            case "DEBIT":
                return new Debit(
                        request.getCardNumber(),
                        LocalDate.parse(request.getExpiryDate()),
                        request.getCvv());
            case "BANK_TRANSFER":
                return new BankTransfer(request.getAccountNumber(), request.getRoutingNumber());
            case "PAYPAL":
                return new PayPal(request.getEmail());
            default:
                throw new RuntimeException("Unknown payment method type: " + type);
        }
    }
}
