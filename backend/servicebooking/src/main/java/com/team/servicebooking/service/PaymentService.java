package com.team.servicebooking.service;

import com.team.servicebooking.config.DatabaseSingleton;
import com.team.servicebooking.dto.PaymentRequestDTO;
import com.team.servicebooking.model.booking.Booking;
import com.team.servicebooking.model.payment.*;
import com.team.servicebooking.repository.BookingRepository;
import com.team.servicebooking.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public PaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    /**
     * Process a payment for a booking.
     * This persists both the Payment and updates the Booking status.
     */
    @Transactional
    public Payment processPayment(PaymentRequestDTO request) throws InterruptedException {
        UUID bookingId = UUID.fromString(request.getBookingId());
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        if (!booking.getState().payable()) {
            throw new RuntimeException("Booking is not payable");
        }

        PaymentMethodStrategy paymentMethod = buildPaymentMethod(request);

        DatabaseSingleton config = DatabaseSingleton.getInstance();
        double price = booking.getService().getPrice();
        double finalPrice = price * config.getDiscount();

        boolean success = paymentMethod.pay(finalPrice);
        if (!success) {
            throw new RuntimeException("Payment failed");
        }

        Payment payment = new Payment(
                booking,
                request.getPaymentMethodType(),
                booking.getService().getPrice());

        booking.addPayment(payment);

        booking.markPaid();

        bookingRepository.save(booking);

        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(UUID id) {
        return paymentRepository.findById(id);
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