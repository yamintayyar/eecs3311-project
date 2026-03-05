package com.team.servicebooking.model.user;

import java.util.List;

import com.team.servicebooking.config.DatabaseSingleton;
import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.booking.Booking;
import com.team.servicebooking.model.payment.Payment;
import com.team.servicebooking.model.payment.PaymentMethodStrategy;
import com.team.servicebooking.model.service.Service;

public class Client implements User {
    private List<PaymentMethodStrategy> paymentMethods;
    private List<Payment> payments;
    private List<Booking> bookings;
    private DatabaseSingleton database;

    Client() {

    }

    void addPaymentMethod(PaymentMethodStrategy paymentMethod) {

    }

    void removePaymentMethod(PaymentMethodStrategy paymentMethod) {

    }

    List<Booking> getBookingHistory() {
        return null;
    }

    List<Payment> getPaymentHistory() {
        return null;
    }

    void cancelBooking(Booking booking) {

    }

    void requestBooking(Consultant consultant, Availability availability, Service service) {

    }

    void processPayment(Booking booking, PaymentMethodStrategy paymentMethod) {

    }

    List<Booking> browseBookings() {
        return null;
    }

    @Override
    public boolean login() {
        return false;
    }

    @Override
    public boolean logout() {
        return false;
    }
}
