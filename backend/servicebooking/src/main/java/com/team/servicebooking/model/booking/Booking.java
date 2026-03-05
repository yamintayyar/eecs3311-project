package com.team.servicebooking.model.booking;

import java.security.Provider;

import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.payment.Payment;
import com.team.servicebooking.model.service.Service;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.model.user.Consultant;

public class Booking {
    private java.util.UUID booking_id;
    private Client client;
    private Consultant consultant;
    private Service service;
    private Availability availability;
    private Booking booking;
    private BookingState bookingState;
    private Payment payment;

    boolean changeState() {
        return false;
    }

}
