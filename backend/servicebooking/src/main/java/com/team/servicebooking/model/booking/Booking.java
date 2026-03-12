package com.team.servicebooking.model.booking;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.payment.Payment;
import com.team.servicebooking.model.service.Service;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.model.user.Consultant;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JsonBackReference
    private Client client;

    @ManyToOne
    @JsonBackReference
    private Consultant consultant;

    @ManyToOne
    private Service service;

    @ManyToMany
    @JoinTable(name = "booking_availability", joinColumns = @JoinColumn(name = "booking_id"), inverseJoinColumns = @JoinColumn(name = "availability_id"))
    private List<Availability> availabilities = new ArrayList<>();

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Payment> payments = new ArrayList<>();

    private String status;

    @Transient
    private BookingState state;

    public Booking() {
    }

    public Booking(Client client, Consultant consultant, Service service, List<Availability> availabilities) {
        this.client = client;
        this.consultant = consultant;
        this.service = service;
        this.availabilities = availabilities;

        changeState(new RequestState(this));
    }

    public UUID getId() {
        return id;
    }

    public Service getService() {
        return service;
    }

    public String getStatus() {
        return status;
    }

    public BookingState getState() {
        return state;
    }

    public Iterable<Availability> getAvailabilities() {
        return availabilities;
    }

    public void changeState(BookingState newState) {
        this.state = newState;
        this.status = newState.getStatus();
    }

    public void markPaid() {
        state.markPaid();
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
    }

}