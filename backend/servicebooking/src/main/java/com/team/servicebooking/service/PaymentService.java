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
import java.util.*;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final ConfigService configService;
    private final PaymentMethodStrategyService paymentMethodService;

    public PaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository, ConfigService configService, PaymentMethodStrategyService paymentMethodService) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.configService = configService;
        this.paymentMethodService = paymentMethodService;
    }

    /**
     * Process a payment for a booking.
     * This persists both the Payment and updates the Booking status.
     */
    @Transactional
    public Payment processPayment(PaymentRequestDTO request) throws InterruptedException { //TODO: use database and service layer instead of model class for payment method
        UUID bookingId = UUID.fromString(request.getBookingId());
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        if (!booking.getState().payable()) {
            throw new RuntimeException("Booking is not payable");
        }

        PaymentMethodStrategy paymentMethod = buildPaymentMethod(request);
        paymentMethod.setClient(booking.getClient()); //set client, for ease of access later

        if (!paymentMethod.validate()) {
                System.out.println("Error: Invalid payment method");
                return null;
            }

        DatabaseSingleton config = configService.getConfiguration();
        double price = booking.getService().getPrice();
        double finalPrice = price * config.getDiscount();

        boolean success = paymentMethod.pay(finalPrice);
        if (!success) {
            throw new RuntimeException("Payment failed");
        }

        Payment payment = new Payment(
                booking,
                request.getPaymentMethodType(),
                paymentMethod, //added reference to the payment method, makes it easier to refer to method when requesting a refund
                booking.getService().getPrice());


        paymentMethodService.addPaymentMethod(paymentMethod); //save payment method

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

    public List<Payment> getPaymentsByClient(UUID clientId) {
        return null; //TODO
    }

    public void refund(Payment payment) {
        //TODO: implement paymentService refund method, which should process refund + mark payment as refunded
    }
}