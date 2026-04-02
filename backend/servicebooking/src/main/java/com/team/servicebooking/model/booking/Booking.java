package com.team.servicebooking.model.booking;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.payment.Payment;
import com.team.servicebooking.model.service.Service;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.model.user.Consultant;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

//    @ManyToOne
    private LocalDate bookingDate;

    @Transient
    private BookingState bookingState;

//    @OneToOne
    @ManyToOne //using many to one to avoid issues when payment is not yet added to booking (could result in primary key conflict otherwise)
    private Payment payment;

//    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<Payment> payments = new ArrayList<>(); // unnecessary, we can keep a 1:1 relationship

    private String status;

    @Transient
    private BookingState state;

    //do we need this?
//    private DatabaseSingleton database = DatabaseSingleton.getInstance();

    public Booking() {
    }

    public Booking(Client client, Consultant consultant, Service service, List<Availability> availabilities) {
        this.client = client;
        this.consultant = consultant;
        this.service = service;
        this.availabilities = availabilities;
        this.bookingDate = LocalDate.now();
        this.bookingState = new RequestState(this);

//        System.out.println("Booking requested for " + client.getName() + "on " + bookingDate.toString() + ".");

        changeState(new RequestState(this));

//        if (database.getVerboseNotification()) {
//            client.notify("Booking created for " + bookingDate.toString() + ".");
//        }

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

    public void cancel() {
        this.bookingState.cancel();
    }

    public void reject() {
        this.bookingState.reject();
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public double getPrice() {
        return service.getPrice();
    }

    public void changeState(BookingState newState) {
        this.state = newState;
        this.status = newState.getStatus();
    }

//    public void pay(Payment payment) {
//        this.payment = payment;
//
//        if (database.getVerboseNotification()) {
//            String notification = "Booking " + booking_id + " has been paid for";
//            client.notify(notification);
//            consultant.notify(notification);
//        }
//    }

    public void markPaid() {
        state.markPaid();
    }

    public boolean paid() {
        return payment != null; // returns true if payment has been set, and thus pay() was called
    }

    public void addPayment(Payment payment) {
//        payments.add(payment);
        this.payment = payment;
    }

    public Payment getPayment() {
        return payment;
    }

    public void confirm() {
        this.bookingState.confirm();
    }

    public void complete() {
        this.bookingState.complete();
    }
}
