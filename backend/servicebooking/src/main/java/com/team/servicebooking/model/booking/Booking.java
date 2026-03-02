package servicebooking.src.main.java.com.team.servicebooking.model.booking;

import servicebooking.src.main.java.com.team.servicebooking.model.availability.Availability;
import servicebooking.src.main.java.com.team.servicebooking.model.payment.Payment;
import servicebooking.src.main.java.com.team.servicebooking.model.service.Service;
import servicebooking.src.main.java.com.team.servicebooking.model.user.Client;
import servicebooking.src.main.java.com.team.servicebooking.model.user.Consultant;

public class Booking {
    private java.util.UUID booking_id;
    private Client client;
    private Consultant consultant;
    private Service service;
    private Availability availability;
    private Booking booking;
    private BookingState bookingState;
    private Payment payment;

    public java.util.UUID getID() {
        return booking_id;
    }

    boolean changeState() {
        return false;
    }

    boolean payable() {
        return false;
    }

    void cancel() {

    }

    void reject() {

    }

    double getPrice() {
        return 0;
    }

}
