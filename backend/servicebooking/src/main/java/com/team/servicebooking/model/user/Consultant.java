package com.team.servicebooking.model.user;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.booking.Booking;
import com.team.servicebooking.model.service.Service;

import jakarta.persistence.*;

@Entity
@Table(name = "consultants")
public class Consultant extends User {

    @OneToMany(mappedBy = "consultant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Service> services = new ArrayList<>();

    /*
     * @OneToMany(mappedBy = "consultant")
     * 
     * @JsonManagedReference
     * private List<Booking> bookings = new ArrayList<>();
     */

    @OneToMany(mappedBy = "consultant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Availability> availabilities = new ArrayList<>();

    protected Consultant() {
    }

    public Consultant(String name, String email, String password) {
        super(name, email, password);
    }

    public List<Service> getServices() {
        return services;
    }

    /*
     * public List<Booking> getBookings() {
     * return bookings;
     * }
     */

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void addService(Service service) {
        services.add(service);
    }

    public void addAvailability(Availability availability) {
        availability.setConsultant(this);
        availabilities.add(availability);
    }
}