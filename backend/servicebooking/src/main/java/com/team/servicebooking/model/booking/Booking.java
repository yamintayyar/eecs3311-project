package com.team.servicebooking.model.booking;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team.servicebooking.config.DatabaseSingleton;
import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.payment.Payment;
import com.team.servicebooking.model.service.Service;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.model.user.Consultant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
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

    private LocalDate bookingDate;
    private BookingState bookingState;
    private Payment payment;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Payment> payments = new ArrayList<>();

    private String status;

    @Transient
    private BookingState state;

    //do we need this?
    private DatabaseSingleton database = DatabaseSingleton.getInstance();

    public Booking() {
    }

    public Booking(Client client, Consultant consultant, Service service, List<Availability> availabilities) {
        this.client = client;
        this.consultant = consultant;
        this.service = service;
        this.availability = availabilties;
        this.bookingDate = LocalDate.now();
        this.bookingState = new RequestState(this);

        System.out.println("Booking requested for " + client.getName() + "on " + bookingDate.toString() + ".");

        changeState(new RequestState(this));

        if (database.getVerboseNotification()) {
            client.notify("Booking created for " + bookingDate.toString() + ".");
        }

    }

    public UUID getId() {
        return id;
    }

    public Client getClient() { //TODO: update these getter methods to use IDs instead of passing objects, to function more properly with database
        return client;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public Service getService() {
        return service;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public BookingState getBookingState() {
        return bookingState;
    }

    public String getStatus() {
        return status;
    }

    public BookingState getState() {
        return state;
    }

    public boolean payable() {
        return this.bookingState.payable();
    }

    public void cancel() { //TODO: we need to standardize the method of changing states, following the examples given in class. either we use changeState, or we don't

        int min_notice = database.getMinNotice();

        LocalDateTime cancel_deadline = availability.get(0).getStartTime().plusHours(-min_notice); // get start time of
                                                                                                   // beginning of
                                                                                                   // session

        if (cancel_deadline.compareTo(LocalDateTime.now()) < 0) {
            System.out.println("Error: Can not cancel booking because deadline has passed.");

            if (database.getVerboseNotification()) {
                client.notify("Error: Can not cancel booking because deadline has passed.");
            }

            return;
        }

        if (bookingState.isRefundable() && database.getRefundPolicy()) { // if booking is in a refundable state (paid)
                                                                         // and the application has a refund policy
                                                                         // active
            payment.markRefunded();
            System.out.println("Successfully refunded payment for booking " + booking_id);

            if (database.getVerboseNotification()) {
                client.notify("Successfully refunded payment for booking " + booking_id);
            }
        }

        this.bookingState.cancel();
    }

    public void reject() {
        this.bookingState.reject();

        client.notify("Booking " + booking_id + " has been rejected by consultant " + consultant.getName());
    }

    public Iterable<Availability> getAvailabilities() {
        return availabilities;
    }

    public double getPrice() {
        return service.getPrice();
    }

    public void changeState(BookingState newState) { //TODO: see above todo
        this.state = newState;
        this.status = newState.getStatus();
    }
    public void pay(Payment payment) {
        this.payment = payment;

        if (database.getVerboseNotification()) {
            String notification = "Booking " + booking_id + " has been paid for";
            client.notify(notification);
            consultant.notify(notification);
        }
    }

    public void markPaid() {
        state.markPaid();
    }

    public boolean paid() {
        return payment != null; // returns true if payment has been set, and thus pay() was called
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    public void confirm() {
        this.bookingState.confirm();
    }

    public void complete() {
        this.bookingState.complete();
    }
}
