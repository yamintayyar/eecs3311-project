package com.team.servicebooking.model.booking;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.UUID;

import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.payment.Payment;
import com.team.servicebooking.model.service.Service;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.model.user.Consultant;

import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.payment.Payment;
import com.team.servicebooking.model.service.Service;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.model.user.Consultant;

public class Booking {
    private UUID booking_id;
    private Client client;
    private Consultant consultant;
    private Service service;
    private List<Availability> availability;
    private LocalDate bookingDate;
    private BookingState bookingState;
    private Payment payment;

    public Booking(Client client, Consultant consultant, Service service, List<Availability> availabilty) {
        this.booking_id = UUID.randomUUID();
        this.client = client;
        this.consultant = consultant;
        this.service = service;
        this.availability = availabilty;
        this.bookingDate = LocalDate.now();
        this.bookingState = new RequestState(this);

        System.out.println("Booking requested for " + client.getName() + "on " + bookingDate.toString() + "."); // TODO:
                                                                                                                // test
                                                                                                                // to
                                                                                                                // make
                                                                                                                // sure
                                                                                                                // that
                                                                                                                // formatting
                                                                                                                // is
                                                                                                                // correct

    }

    public java.util.UUID getID() {
        return booking_id;
    }

    public void changeState(BookingState bookingState) {
        // Changed return type from boolean to void.
        this.bookingState = bookingState;
    }

    public boolean payable() {
        return this.bookingState.payable();
    }

    public void cancel() {
        this.bookingState.cancel();
    }

    public void reject() {
        this.bookingState.reject();
    }

    public double getPrice() {
        return service.getPrice();
    }

    public void pay(Payment payment) {
        this.payment = payment;
    }

    public boolean paid() {
        return payment != null; // returns true if payment has been set, and thus pay() was called
    }

    public void confirm() {
        this.bookingState.confirm();
    }

    public void complete() {
        this.bookingState.complete();
    }
}
