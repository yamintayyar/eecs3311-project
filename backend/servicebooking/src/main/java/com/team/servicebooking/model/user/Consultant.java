package com.team.servicebooking.model.user;

import java.util.List;

import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.booking.Booking;
import com.team.servicebooking.model.service.Service;

public class Consultant implements User {
    private List<Service> services;
    private List<Availability> availabilitySlots;
    private List<Booking> bookings;

    Consultant() {

    }

    void addAvailability() {

    }

    void removeAvailability(Availability availability) {

    }

    List<Booking> viewBookings() {
        return null;
    }

    void acceptBooking(Booking booking) {

    }

    void rejectBooking(Booking booking) {

    }

    void completeBooking(Booking booking) {

    }

    List<Service> getServices() {
        return null;
    }

    List<Availability> getAvailabilities() {
        return null;
    }

    void notify(Booking booking) { // do we need this?

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
